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

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.sound.sampled.*;

public class WaveFormPanel extends JPanel {
    private byte[] audioData;

    public WaveFormPanel() {
        super();

        setBorder(BorderFactory.createLineBorder(Color.gray));
        this.setBackground(new Color(0x000000));


        audioData = null;
    }

    public void setAudioData(byte[] audioData) {
        this.audioData = audioData;
        repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 

        if (audioData != null)
            drawWaveForm(g);
        
        g.setColor(Color.white);
        g.drawLine(0, getHeight()/2, getWidth(), getHeight()/2);
    }

    private void drawWaveForm(Graphics g) {
        int width = this.getWidth();
        int height = this.getHeight();

        int[] waveFormXCoords = new int[width];
        int[] waveFormYCoords = new int[width];
        

        int step = audioData.length / width;
        double yScale = (double)height / 255.0;

        waveFormXCoords[0] = 0;
        waveFormYCoords[0] = height/2;

        for (int i = 1; i < width; i++) {
            byte sample = audioData[i * step];

            waveFormXCoords[i] = i;
            waveFormYCoords[i] = height/2 - (int)(sample*yScale);
        }

        g.setColor(Color.green);
        g.drawPolyline(waveFormXCoords, waveFormYCoords, width);
    }
}
