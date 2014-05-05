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
import java.net.URL;

public class AudioManager {
    private byte[] audioData;
    private WaveFormPanel waveFormPanel;
    private boolean isRecording;


    private synchronized void setAudioData(byte[] audioData) {
        this.audioData = audioData;

        waveFormPanel.setAudioData(audioData);
    }

    public void setWaveFormPanel(WaveFormPanel waveFormPanel) {
        this.waveFormPanel = waveFormPanel;
    }

    public void startRecording() throws LineUnavailableException {
        final AudioFormat format = Utils.getFormat();
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
        final TargetDataLine line = (TargetDataLine)AudioSystem.getLine(info);

        line.open(format);
        line.start();

        Runnable runner = new Runnable() {
            int bufferSize = (int)format.getSampleRate() * format.getFrameSize();
            byte buffer[] = new byte[bufferSize];

            public void run() {
                ByteArrayOutputStream recordedSound = new ByteArrayOutputStream();
                isRecording = true;
                try {
                    while (isRecording) {
                        int count = line.read(buffer, 0, buffer.length);
                        if (count > 0) {
                            recordedSound.write(buffer, 0, count);
                        }
                    }
                    recordedSound.close();
                    byte[] audioData = recordedSound.toByteArray();
                    setAudioData(audioData);

                } catch (IOException e){
                    // TODO find a way to handle this
                    System.exit(-1);
                }
            }
        };

        Thread captureThread = new Thread(runner);
        captureThread.start();
    }

    public boolean stopRecording() {
        boolean wasRecording = isRecording;
        isRecording = false;
        return wasRecording;
    }
}
