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
import java.net.*;
import javax.sound.sampled.*;

public class Utils {
    // TODO put this somewhere else or get rid of it
    // altogether
    public static AudioFormat getFormat() {
        AudioFormat.Encoding encoding = AudioFormat.Encoding.PCM_SIGNED;
        float rate = 44100.0F;
        int sampleSize = 8;
        boolean bigEndian = true;
        int channels = 1;

        return new AudioFormat(encoding, rate, sampleSize,
                channels, (sampleSize / 8) * channels, rate, bigEndian);
    }

    public static byte[] loadFile(String path) throws IOException {
        RandomAccessFile f = new RandomAccessFile(path, "r");
        try {
            byte[] data = new byte[(int)f.length()];
            f.readFully(data);
            return data;
        } finally {
            f.close();
        }
    }
    
    public static byte[] loadUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        ByteArrayOutputStream bais = new ByteArrayOutputStream();
        
        InputStream is = null;
        try {
            is = url.openStream();
            byte[] byteChunk = new byte[4096]; // Or whatever size you want to read in at a time.
            int n;

            while ( (n = is.read(byteChunk)) > 0 ) {
                bais.write(byteChunk, 0, n);
            }
        } finally {
            if (is != null) { is.close(); }
        }
        return bais.toByteArray();
    }
}

