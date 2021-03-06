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

package skygge.UI;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import skygge.Utils;

/**
 *
 * @author json
 */
public class SentenceInfoFrame extends javax.swing.JFrame {

    /**
     * Creates new form SentenceInfoFrame
     */
    public SentenceInfoFrame() {
        initComponents();
        
        Utils.setIconOnFrame(this);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        infoTextArea = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setTitle("Sentence Info");
        setLocation(new java.awt.Point(200, 200));
        setSize(new java.awt.Dimension(320, 140));

        jTabbedPane1.setToolTipText("");

        jPanel1.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setMinimumSize(null);
        jScrollPane1.setPreferredSize(new java.awt.Dimension(310, 135));

        infoTextArea.setEditable(false);
        infoTextArea.setColumns(20);
        infoTextArea.setRows(5);
        infoTextArea.setText("Chinese (simp): 燕子的尾巴像剪刀。\nPinyin: Yan4zi de wei3ba xiang4 jian3dao1.\nEnglish: The swallow has a forked tail.");
        infoTextArea.setMinimumSize(null);
        infoTextArea.setPreferredSize(null);
        infoTextArea.setSize(new java.awt.Dimension(300, 125));
        jScrollPane1.setViewportView(infoTextArea);

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jLabel1.setText("Select text and press ctrl-c to copy.");
        jPanel1.add(jLabel1, java.awt.BorderLayout.PAGE_END);

        jTabbedPane1.addTab("Sentence Info", jPanel1);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setText("To give feedback, please leave a comment on http://skygge.zaoyin.eu\nIf you want to report bugs or contribute features, please go to https://github.com/jsoendermann/Skygge\n\n=====================\n\nThe sentence audio and information is copyright (C) 2014, J. Soendermann.\n\n=====================\n\nThe program and its source are released under the GNU GPL version 2 or later:\n\nCopyright (C) 2014, J. Soendermann\n\nThis program is free software; you can redistribute it and/or\nmodify it under the terms of the GNU General Public License\nas published by the Free Software Foundation; either version 2\nof the License, or (at your option) any later version.\n\nThis program is distributed in the hope that it will be useful,\nbut WITHOUT ANY WARRANTY; without even the implied warranty of\nMERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the\nGNU General Public License for more details.\n\nYou should have received a copy of the GNU General Public License\nalong with this program; if not, write to the Free Software\nFoundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.\n\n=====================\n\nThe icons used in the program are released under the GNU GPL version 3.  They can be downloaded at https://code.google.com/p/faenza-icon-theme/\n\n=====================\n\nThis program uses the json-smart library which can be downloaded at https://code.google.com/p/json-smart/ and is released under the Apache License 2.0"); // NOI18N
        jScrollPane2.setViewportView(jTextArea1);

        jPanel2.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("About Skygge", jPanel2);

        getContentPane().add(jTabbedPane1, java.awt.BorderLayout.CENTER);
        jTabbedPane1.getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // TODO make sure this method updates (redraws?) the frame when the
    // information changes
    public void setInformation(List<Entry<String, String>> information) {
        String s = "";
        
        for (Entry<String, String> entry : information) {
            s += (String)entry.getKey();
            s += ": ";
            s += (String)entry.getValue();
            s += "\n";
            
            infoTextArea.setText(s);
        }
        
        // TODO repaint/update
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea infoTextArea;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
