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

import javax.sound.sampled.*;

public class Utils {
    public static AudioFormat getFormat() {
        AudioFormat.Encoding encoding = AudioFormat.Encoding.PCM_SIGNED;
        float rate = 44100.0F;
        int sampleSize = 8;
        boolean bigEndian = true;
        int channels = 1;

        return new AudioFormat(encoding, rate, sampleSize,
                channels, (sampleSize / 8) * channels, rate, bigEndian);
    }


}

