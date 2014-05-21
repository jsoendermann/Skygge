/*
 * Copyright (C) 2014 J. Soendermann
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package skygge;

import java.io.*;
import javax.sound.sampled.*;


public class SoundDeviceManager extends Thread {
    private static SoundDeviceManager instance = null;
    
    private Object stateLock;

    private State currentState;
    private State nextState;

    // Used for both playing and recording
    private byte[] buffer;

    // Used for playing
    private byte[] audioDataToBePlayed;
    private AudioInputStream audioInputStream;
    private SourceDataLine sourceDataLine;

    // Used for recording
    private TargetDataLine targetDataLine;
    private ByteArrayOutputStream recordStream;
    private byte[] recordedAudioData;


    private enum State {
        IDLE, PLAYING, LOOPING, RECORDING
    }


    // This class is a singleton, getInstance() is used
    // to the the instance
    protected SoundDeviceManager() {
        this.nextState = State.IDLE;
        this.currentState = State.IDLE;
        
        stateLock = new Object();
    }

    public static SoundDeviceManager getInstance() {
        if (instance == null) {
            instance = new SoundDeviceManager();
            instance.start();
        } 
        return instance;
    }


    public synchronized void startPlaying(byte[] audioDataToBePlayed) {
        //System.out.println("startPlaying");
        stopEverything();
        synchronized (stateLock) {
            this.audioDataToBePlayed = audioDataToBePlayed;
            nextState = State.PLAYING;
        }
    }

    public synchronized void startLooping(byte[] audioDataToBePlayed) {
        //System.out.println("startLooping");
        stopEverything();
        synchronized (stateLock) {
            this.audioDataToBePlayed = audioDataToBePlayed;
            nextState = State.LOOPING;
        }
    }


    public synchronized void startRecording() {
        //System.out.println("startRecording");
        stopEverything();
        synchronized (stateLock) {
            nextState = State.RECORDING;
        }
    }

    // This method blocks until the SoundDeviceManager has stopped what it 
    // was doing before and returns to the IDLE state
    public synchronized void stopEverything() {
        //System.out.println("stopEverything");
        synchronized (stateLock) {
            nextState = State.IDLE;
        }
        
        //System.out.println("set next state to idle");

        State currentStateCopy;
        
        do {
        //    System.out.println("entered loop");
            
            synchronized (stateLock) {
                currentStateCopy = currentState;
                
                // This should not happen
                if (nextState != State.IDLE) {
                    System.exit(-20);
                }
            }
            
         //   System.out.println("left synchronized block in loop");
         //   System.out.println("currentStateCopy is " + currentStateCopy);
            Thread.yield();
        } while (currentStateCopy != State.IDLE);
    }

    public synchronized byte[] getRecordedAudioData() {
        return recordedAudioData;
    }


    private String stateToString(State s) {
        switch (s) {
            case IDLE: return "IDLE";
            case PLAYING: return "PLAYING";
            case LOOPING: return "LOOPING";
            case RECORDING: return "RECORDING";
            default: return "";
        }
    }

    private State lastCurrentState = State.IDLE, lastNextState = State.IDLE;
    private void printIfChanged() {
        if (lastCurrentState != currentState || lastNextState != nextState) {
            System.out.println("currentState: " + stateToString(currentState) +
                    " nextState: " + stateToString(nextState));
            lastCurrentState = currentState;
            lastNextState = nextState;
        }
    }
    
    // run is a public method but it is called by the getInstance
    public void run() {
        while (true) {
            synchronized (stateLock) {
                printIfChanged();
                switch (currentState) {
                    case IDLE: 
                        switch (nextState) {
                            case PLAYING:
                                setUpPlayingObjects();
                                currentState = State.PLAYING;
                                break;
                            case LOOPING:
                                setUpPlayingObjects();
                                currentState = State.LOOPING;
                                break;
                            case RECORDING:
                                setUpRecordingObjects();
                                currentState = State.RECORDING;
                                break;
                            case IDLE:
                                break;
                            }
                        break;

                    case PLAYING:
                        switch (nextState) {
                            case PLAYING:
                                play();
                                break;
                            case IDLE:
                                shutDownPlayingObjects();
                                currentState = State.IDLE;
                                break;
                            case LOOPING:
                            case RECORDING:
                                // This should not happen,
                                // the class should always go 
                                // back to the IDLE state before  
                                // changing to a new state
                                // TODO handle this
                                System.exit(-14);
                                break;
                        }
                        break;

                    case LOOPING:
                        switch (nextState) {
                            case LOOPING:
                                loop();
                                break;
                            case IDLE:
                                shutDownPlayingObjects();
                                currentState = State.IDLE;
                                break;
                            case PLAYING:
                            case RECORDING:
                                // This should not happen,
                                // the class should always go 
                                // back to the IDLE state before  
                                // changing to a new state
                                // TODO handle this
                                System.exit(-15);
                                break;
                        }
                        break;

                    case RECORDING:
                        switch (nextState) {
                            case RECORDING:
                                record();
                                break;
                            case IDLE:
                                shutDownRecordingObjects();
                                currentState = State.IDLE;
                                break;
                            case PLAYING:
                            case LOOPING:
                                // This should not happen,
                                // the class should always go 
                                // back to the IDLE state before  
                                // changing to a new state
                                // TODO handle this
                                System.exit(-16);
                                break;
                        }
                        break;
                }
                /*System.out.println("finished state loop");
                try {
                Thread.sleep(333);
                } catch (Exception e) {}
                */
            }
            Thread.yield();
        }
    }

    private void setUpPlayingObjects() {
        AudioFormat format = Utils.getFormat();

        // Set up input stream from audioDataToBePlayed
        InputStream input = new ByteArrayInputStream(audioDataToBePlayed);
        audioInputStream = new AudioInputStream(input, format, audioDataToBePlayed.length / format.getFrameSize());

        // Set up output Line
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
        try {
            sourceDataLine = (SourceDataLine)AudioSystem.getLine(info);
            sourceDataLine.open(format);
            sourceDataLine.start();
        } catch (Exception e) {
            // TODO
            System.exit(-10);
        }

        // TODO put magic number somewhere else. make it small so that audio can be cut off quickly
        buffer = new byte[100];
    }

    private void play() {
        try {
            int count = audioInputStream.read(buffer, 0, buffer.length);
            if (count > 0) {
                sourceDataLine.write(buffer, 0, count);
            } else {
                shutDownPlayingObjects();
                // TODO synchronize this w/ public functions
                nextState = currentState = State.IDLE;
            }

        } catch (Exception e) {
            // TODO
            System.exit(-11);
        }
    }

    private void loop() {
        try {
            int count = audioInputStream.read(buffer, 0, buffer.length);
            if (count > 0) {
                sourceDataLine.write(buffer, 0, count);
            } else {
                audioInputStream.reset();
            }

        } catch (Exception e) {
            // TODO
            System.exit(-11);
        }
    }

    private void shutDownPlayingObjects() {
        sourceDataLine.drain();
        sourceDataLine.close();
    }

    private void setUpRecordingObjects() {
        try {
            final AudioFormat format = Utils.getFormat();

            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            targetDataLine = (TargetDataLine)AudioSystem.getLine(info);

            targetDataLine.open(format);
            targetDataLine.start();

            recordStream = new ByteArrayOutputStream();

            // TODO put magic number somewhere else. make it small so that audio can be cut off quickly
            buffer = new byte[100];
        } catch (Exception e) {
            System.exit(-12);
        }
    }

    private void record() {
        int count = targetDataLine.read(buffer, 0, buffer.length);
        if (count > 0) {
            recordStream.write(buffer, 0, count);
        } else {
            shutDownRecordingObjects();
            nextState = currentState = State.IDLE;
        }
    }

    private void shutDownRecordingObjects() {
        try {
            recordStream.close();
        } catch (Exception e) {
            System.exit(-13);
        }
        
        targetDataLine.close();
        
        recordedAudioData = recordStream.toByteArray();
    }



}
