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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultListModel;
import net.minidev.json.*;
import net.minidev.json.parser.ParseException;
import skygge.Sentence;
import skygge.SentencePack;
import skygge.Utils;

/**
 *
 * @author json
 */
public class SentenceLibraryFrame extends javax.swing.JFrame {
    private SkyggeFrame skyggeFrame;
    
    private List<SentencePack> sentencePacks;
    
    /**
     * Creates new form SentenceLibraryFrame
     */
    public SentenceLibraryFrame(SkyggeFrame skyggeFrame) {
        initComponents();
        
        this.skyggeFrame = skyggeFrame;
        
        sentencePacks = new ArrayList<SentencePack>();
        
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    String sentenceDataString = Utils.loadUrlIntoString("https://skygge.s3.amazonaws.com/sentence_data.json");
                    
                    Object sentenceData = JSONValue.parseStrict(sentenceDataString);
                    JSONObject sentenceDataObject = (JSONObject)sentenceData;
                    
                    JSONObject languagesMap = (JSONObject)sentenceDataObject.get("languages");
                    JSONArray chineseSentencePacksData = (JSONArray)languagesMap.get("Chinese");
                    
                    for (int i = 0; i < chineseSentencePacksData.size(); i++) {
                        sentencePacks.add(new SentencePack((JSONObject)chineseSentencePacksData.get(i)));
                    }
                    
                    
                    DefaultListModel sentencePackListModel = new DefaultListModel();
                    
                    for (int i = 0; i < sentencePacks.size(); i++) {
                        sentencePackListModel.addElement(sentencePacks.get(i));
                    }
                    
                    
                    sentencePackList.setModel(sentencePackListModel);
                    sentencePackList.setSelectedIndex(0);
                    
                    sentencePackSelected(0);
                } catch (IOException e) {
                    System.exit(-31);
                } catch (ParseException e) {
                    System.exit(-32);
                }
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
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        jPanel1.add(cancelButton);

        openButton.setText("Open");
        openButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openButtonActionPerformed(evt);
            }
        });
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
        sentencePackList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                sentencePackListValueChanged(evt);
            }
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

    private void openButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openButtonActionPerformed
        Sentence selectedSentence = (Sentence)sentenceList.getSelectedValue();
        skyggeFrame.loadSentence(selectedSentence);
        this.setVisible(false);
    }//GEN-LAST:event_openButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void sentencePackListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_sentencePackListValueChanged
        int index = sentencePackList.getSelectedIndex();
        sentencePackSelected(index);
        
    }//GEN-LAST:event_sentencePackListValueChanged

    private void sentencePackSelected(int index) {
        SentencePack sentencePack = sentencePacks.get(index);
        
        DefaultListModel sentenceListModel = new DefaultListModel();
        
        for (Sentence sentence : sentencePack.getSentences()) {
            sentenceListModel.addElement(sentence);
        }
        
        sentenceList.setModel(sentenceListModel);
    }

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
