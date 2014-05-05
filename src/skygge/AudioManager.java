/*
 * Copyright (C) 2014 json
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


public class AudioManager extends Thread {
    private static AudioManager audioManagerInstance = null;

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



    // Make this class a singleton
    protected AudioManager() {
        this.nextState = State.IDLE;
        this.currentState = State.IDLE;
    }

    public static AudioManager getAudioManagerInstance() {
        if (audioManagerInstance == null) {
            audioManagerInstance = new AudioManager();
            audioManagerInstance.start();
        } 
        return audioManagerInstance;
    }


    // TODO does this method have to be synchronized?
    // This method tells the audio manager to stop what it's doing and return
    // to IDLE state. One it has done so, it sets the audioDataToBePlayed
    // member and sets the next state to PLAYING, which causes
    // the audio manager to set up the playing objects
    public synchronized void startPlaying(byte[] audioDataToBePlayed) {
        stopEverything();
        this.audioDataToBePlayed = audioDataToBePlayed;
        nextState = State.PLAYING;
    }

    // TODO does this method have to be synchronized?
    public synchronized void stopPlaying() {
        // TODO maybe throw an exception or return false if
        // the audio manager is not in PLAYING state
        stopEverything();
    }

    public synchronized void startLooping(byte[] audioDataToBePlayed) {
        stopEverything();
        this.audioDataToBePlayed = audioDataToBePlayed;
        nextState = State.LOOPING;
    }

    public synchronized void stopLooping() {
        stopEverything();
    }


    public synchronized void startRecording() {
        stopEverything();
        nextState = State.RECORDING;
    }

    public synchronized void stopRecording() {
        // TODO maybe throw an exception or return false if
        // the audio manager is not in RECORDING state
        stopEverything();
    }

    // TODO should this be synchronized?
    public byte[] getRecordedAudioData() {
        return recordedAudioData;
    }

    // This method blocks until the audio manager stops what it was doing before
    // and returns to IDLE state
    public synchronized void stopEverything() {
        nextState = State.IDLE;
        while (currentState != State.IDLE)
            Thread.yield();
    }


    public void run() {
        while (true) {
            switch (currentState) {
                case IDLE: 
                    if (nextState == State.PLAYING) {
                        setUpPlayingObjects();
                        currentState = State.PLAYING;
                    } else if (nextState == State.LOOPING) {
                        setUpPlayingObjects();
                        currentState = State.LOOPING;
                    } else if (nextState == State.RECORDING) {
                        setUpRecordingObjects();
                        currentState = State.RECORDING;
                    }
                    Thread.yield();
                    break;
                case PLAYING:
                    if (nextState == State.IDLE) {
                        shutDownPlayingObjects();
                        currentState = State.IDLE;
                    } else {
                        play();
                    }
                    break;
                case RECORDING:
                    if (nextState == State.IDLE) {
                        shutDownRecordingObjects();
                        currentState = State.IDLE;
                    } else {
                        record();
                    }
                    break;
                case LOOPING:
                    if (nextState == State.IDLE) {
                        shutDownPlayingObjects();
                        currentState = State.IDLE;
                    } else {
                        loop();
                    }
                    break;

            }
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
            // TODO synchronize this w/ public functions
            nextState = currentState = State.IDLE;
        }
    }

    private void shutDownRecordingObjects() {
        try {
        recordStream.close();
        } catch (Exception e) {
            System.exit(-13);
        }
        // TODO close targetDataLine?
        recordedAudioData = recordStream.toByteArray();
    }


    private enum State {
        IDLE, PLAYING, LOOPING, RECORDING
    }
}