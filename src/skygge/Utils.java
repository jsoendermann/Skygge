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

import java.awt.Image;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.sound.sampled.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

public class Utils {
    private static Image logoImage = null;
        
    // TODO put this somewhere else or get rid of it
    // altogether
    // TODO get this from available formats for recording
    // when recording/playing back recorded audio,
    // hardcode it for audiodata generated by sox
    public static AudioFormat getFormat() {
        AudioFormat.Encoding encoding = AudioFormat.Encoding.PCM_SIGNED;
        float rate = 44100.0F;
        int sampleSize = 8;
        boolean bigEndian = true;
        int channels = 1;

        return new AudioFormat(encoding, rate, sampleSize,
                channels, (sampleSize / 8) * channels, rate, bigEndian);
    }

    public static byte[] loadFileIntoByteArray(String path) throws IOException {
        RandomAccessFile f = new RandomAccessFile(path, "r");
        try {
            byte[] data = new byte[(int)f.length()];
            f.readFully(data);
            return data;
        } finally {
            f.close();
        }
    }
    
    public static byte[] loadUrlIntoByteArray(String urlString) throws IOException {
        URL url = new URL(urlString);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        InputStream inputStream = null;
        
        try {
            inputStream = url.openStream();
            byte[] byteChunk = new byte[8192];
            int n;

            while ((n = inputStream.read(byteChunk)) > 0 ) {
                outputStream.write(byteChunk, 0, n);
            }
        } finally {
            if (inputStream != null) { 
                inputStream.close(); 
            }
        }
        return outputStream.toByteArray();
    }
    
    public static byte[] loadResourceIntoByteArray(String resourcePath) throws IOException { 
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        InputStream inputStream = null;
        
        try {
            inputStream = Skygge.class.getResourceAsStream(resourcePath);
            byte[] byteChunk = new byte[8192];
            int n;

            while ((n = inputStream.read(byteChunk)) > 0 ) {
                outputStream.write(byteChunk, 0, n);
            }
        } finally {
            if (inputStream != null) { 
                inputStream.close(); 
            }
        }
        return outputStream.toByteArray();
    }
    
    public static String loadUrlIntoString(String urlString) throws IOException {
        return new String(loadUrlIntoByteArray(urlString));
    }
    
    public static void setIconOnFrame(JFrame frame) {
        if (logoImage == null) {
            URL logoResourceURL = frame.getClass().getResource("/resources/SkyggeLogo.png");
            ImageIcon logoImageIcon = new ImageIcon(logoResourceURL);
            logoImage = logoImageIcon.getImage();
        }
        frame.setIconImage(logoImage);
    }
}