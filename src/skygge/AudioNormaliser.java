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


public class AudioNormaliser {
    private byte[] audioData;
    
    private final int SILENCE_THRESHOLD = 10;
    
    public AudioNormaliser(byte[] audioData) {
        this.audioData = audioData;
    }
    
    public byte getAverageLevel() {
        int counter = 0, sum = 0;
        
        for (int i = 0; i < audioData.length; i++) {
            byte sample = audioData[i];
            byte sample_abs = (byte)Math.abs(sample);
            
            if (sample_abs > SILENCE_THRESHOLD) {
                sum += sample_abs;
                counter++;
            }
        }
        
        if (counter > 0)
            return (byte)(sum/counter);
        else
            return 0;
    }
    
    public byte[] getNormalisedAudioData(byte averageLevel) {
        byte currentAverage = this.getAverageLevel();
        
        float ratio = (float)averageLevel/currentAverage;
        
        byte[] normalisedAudioData = new byte[audioData.length];
        
        for (int i = 0; i < audioData.length; i++) {
            byte currentSample = audioData[i];
            int newSample = (int)(currentSample*ratio);
            
            if (newSample > 127)
                newSample = 127;
            if (newSample < -128)
                newSample = -128;
            
            normalisedAudioData[i] = (byte)newSample;
        }
        
        return normalisedAudioData;
    }
}
