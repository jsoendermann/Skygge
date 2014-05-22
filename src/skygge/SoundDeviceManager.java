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
import javax.swing.JOptionPane;


public class SoundDeviceManager extends Thread {
    private static SoundDeviceManager instance = null;
    
    // This lock protects access to the currentState
    // and nextState variables. It is used to synchronise
    // between the SoundDeviceManager thread and calling threads.
    private Object stateLock;

    private State currentState;
    private State nextState;

    // Temp buffer used for both playing and recording
    private byte[] buffer;
    // This should be sufficiently small, so that the SoundDeviceManager
    // can react quickly to user input.
    private int BUFFER_SIZE = 100;

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
    // to obtain its instance.
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
        stopEverything();
        synchronized (stateLock) {
            this.audioDataToBePlayed = audioDataToBePlayed;
            nextState = State.PLAYING;
        }
    }

    public synchronized void startLooping(byte[] audioDataToBePlayed) {
        stopEverything();
        synchronized (stateLock) {
            this.audioDataToBePlayed = audioDataToBePlayed;
            nextState = State.LOOPING;
        }
    }


    public synchronized void startRecording() {
        stopEverything();
        synchronized (stateLock) {
            nextState = State.RECORDING;
        }
    }

    // This method blocks until the SoundDeviceManager has stopped what it 
    // was doing before and returns to the IDLE state
    public synchronized void stopEverything() {
        synchronized (stateLock) {
            nextState = State.IDLE;
        }

        State currentStateCopy;
        do {
            synchronized (stateLock) {
                currentStateCopy = currentState;
                
                // TODO handle this
                if (nextState != State.IDLE) {
                    System.exit(-20);
                }
            }

            Thread.yield();
        } while (currentStateCopy != State.IDLE);
    }

    public synchronized byte[] getRecordedAudioData() {
        return recordedAudioData;
    }

    
    // run is a public method but it is called by getInstance
    // and should not be called by threads that use the SoundDeviceManager
    public void run() {
        while (true) {
            synchronized (stateLock) {
                // TODO break this up into several functions
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
                                break;
                        }
                        break;
                }
            }
            Thread.yield();
        }
    }

    private void setUpPlayingObjects() {
        AudioFormat format = Utils.getFormat();

        // Set up input stream from audioDataToBePlayed array
        InputStream input = new ByteArrayInputStream(audioDataToBePlayed);
        audioInputStream = new AudioInputStream(input, format, audioDataToBePlayed.length / format.getFrameSize());

        // Set up output Line
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
        try {
            sourceDataLine = (SourceDataLine)AudioSystem.getLine(info);
            sourceDataLine.open(format);
            sourceDataLine.start();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Unable to set up speakers for playback.", 
                    "Audio Error", JOptionPane.ERROR_MESSAGE);
            currentState = nextState = State.IDLE;
        }

        buffer = new byte[BUFFER_SIZE];
    }

    private void play() {
        try {
            int count = audioInputStream.read(buffer, 0, buffer.length);
            if (count > 0) {
                sourceDataLine.write(buffer, 0, count);
            } else {
                shutDownPlayingObjects();
                nextState = currentState = State.IDLE;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Unable to play audio data.", 
                    "Audio Error", JOptionPane.ERROR_MESSAGE);
            currentState = nextState = State.IDLE;
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
            JOptionPane.showMessageDialog(null, "Unable to loop audio data.", 
                    "Audio Error", JOptionPane.ERROR_MESSAGE);
            currentState = nextState = State.IDLE;
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

            buffer = new byte[BUFFER_SIZE];
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Unable to set up microphone for recording. Do you have a working microphone?", 
                    "Audio Error", JOptionPane.ERROR_MESSAGE);
            currentState = nextState = State.IDLE;
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
            // It's probably safe to ignore this
        }
        
        targetDataLine.close();
        
        recordedAudioData = recordStream.toByteArray();
    }
}