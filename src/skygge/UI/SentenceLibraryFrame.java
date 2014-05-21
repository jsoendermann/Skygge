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

package skygge.UI;

import java.awt.*;
import java.io.IOException;

/**
 *
 * @author json
 */
public class SentenceLibraryFrame extends javax.swing.JFrame {

    /**
     * Creates new form SentenceLibraryFrame
     */
    public SentenceLibraryFrame() {
        initComponents();
        
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                /*try {
                    loadUrlIntoString("https://skygge.s3.amazonaws.com/sentence_data.json");
                } catch (IOException e) {
                    System.exit(-31);
                }*/
            }
        });
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        cancelButton = new javax.swing.JButton();
        openButton = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        sentencePackList = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        sentenceList = new javax.swing.JList();

        setLocation(new java.awt.Point(100, 100));

        jPanel1.setMinimumSize(new java.awt.Dimension(178, 70));
        jPanel1.setPreferredSize(new java.awt.Dimension(453, 40));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        cancelButton.setText("Cancel");
        jPanel1.add(cancelButton);

        openButton.setText("Open");
        jPanel1.add(openButton);

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_END);

        jSplitPane1.setDividerLocation(100);
        jSplitPane1.setDividerSize(5);
        jSplitPane1.setMinimumSize(new java.awt.Dimension(500, 300));
        jSplitPane1.setPreferredSize(new java.awt.Dimension(500, 300));

        sentencePackList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Loading..." };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(sentencePackList);

        jSplitPane1.setLeftComponent(jScrollPane1);

        sentenceList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Loading..." };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(sentenceList);

        jSplitPane1.setRightComponent(jScrollPane2);

        getContentPane().add(jSplitPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JButton openButton;
    private javax.swing.JList sentenceList;
    private javax.swing.JList sentencePackList;
    // End of variables declaration//GEN-END:variables
}
