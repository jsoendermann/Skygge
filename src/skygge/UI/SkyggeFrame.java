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

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import net.minidev.json.parser.ParseException;
import skygge.Sentence;
import skygge.Skygge;
import skygge.SoundDeviceManager;
import skygge.Utils;


public class SkyggeFrame extends javax.swing.JFrame {
    private byte[] sentenceAudioData;
    private byte[] recordingAudioData;

    private SentenceLibraryFrame sentenceLibraryFrame;
    private SentenceInfoFrame sentenceInfoFrame;

    private boolean isRecording = false;

    private String URL_SKYGGE_INFO = "https://skygge.s3.amazonaws.com/skyyge_info.json";

    private String STATUS_BAR_MESSAGE_UPDATE = "<html><font color=red>Your version of Skygge is outdated. To update, please go to http://skygge.zaoyin.eu.</font></html>";
    private String STATUS_BAR_MESSAGE_THANK_YOU = "Thanks for using Skygge.";


    public SkyggeFrame() {
        initComponents();

        sentenceLibraryFrame = new SentenceLibraryFrame(this);
        sentenceInfoFrame = new SentenceInfoFrame();

        checkForNewVersionAndUpdateMOTD();
        loadDefaultSentence();
        setUpHotkeys();
    }

    // this is an asynchronous method
    private void checkForNewVersionAndUpdateMOTD() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    String skyggeInfoString = Utils.loadUrlIntoString(URL_SKYGGE_INFO);
                    JSONObject skyggeInfoData = (JSONObject)JSONValue.parseStrict(skyggeInfoString);

                    String newestVersion = (String)skyggeInfoData.get("newest_version");
                    String messageOfTheDay = (String)skyggeInfoData.get("message_of_the_day");

                    if (!newestVersion.equals(Skygge.VERSION)) {
                        statusBarLabel.setText(STATUS_BAR_MESSAGE_UPDATE);
                    } else {
                        statusBarLabel.setText(messageOfTheDay);
                    }
                } catch (IOException e) {
                    statusBarLabel.setText(STATUS_BAR_MESSAGE_THANK_YOU);
                    // Do nothing, check version the next time the user is online
                } catch (ParseException e) {
                    statusBarLabel.setText(STATUS_BAR_MESSAGE_THANK_YOU);
                    // This shouldn't happen, but it can be ignored.
                }
            }
        });
    }

    // this is an asychronous method
    private void loadDefaultSentence() {
        try {
            String sentencePath = getClass().getResource("/resources/animals2_001.wav").getPath();
            sentenceAudioData = Utils.loadFileIntoByteArray(sentencePath);

            // Audio files generated by sox always have their msb flipped
            // TODO find out why
            for (int i = 0; i < sentenceAudioData.length; i++)
                sentenceAudioData[i] += 128;

            sentenceWaveFormPanel.setAudioData(sentenceAudioData);
        } catch (Exception e) {
            // Silently ignore this error, maybe loading sentences from the internet will work,
            // even though this really shouldn't happen.
        }
    }

    private String HOTKEY_ACTION_PLAY_SENTENCE = "play sentence button clicked";
    private String HOTKEY_ACTION_LOOP_SENTENCE = "loop sentence button clicked";
    private String HOTKEY_ACTION_PLAY_RECORDING = "play recording button clicked";
    private String HOTKEY_ACTION_RECORD_RECORDING = "record sentence button clicked";

    private void setUpHotkeys() {
        InputMap inputMap = rootPanel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(KeyStroke.getKeyStroke('a'), HOTKEY_ACTION_PLAY_SENTENCE);
        inputMap.put(KeyStroke.getKeyStroke('s'), HOTKEY_ACTION_LOOP_SENTENCE);
        inputMap.put(KeyStroke.getKeyStroke('d'), HOTKEY_ACTION_PLAY_RECORDING);
        inputMap.put(KeyStroke.getKeyStroke('f'), HOTKEY_ACTION_RECORD_RECORDING);

        ActionMap actionMap = rootPanel.getActionMap();
        actionMap.put(HOTKEY_ACTION_PLAY_SENTENCE, new PressButtonAction(playSentenceButton));
        actionMap.put(HOTKEY_ACTION_LOOP_SENTENCE, new PressButtonAction(loopSentenceButton));
        actionMap.put(HOTKEY_ACTION_PLAY_RECORDING, new PressButtonAction(playRecordingButton));
        actionMap.put(HOTKEY_ACTION_RECORD_RECORDING, new PressButtonAction(recordRecordingButton));  
    }

    class PressButtonAction extends AbstractAction {
        AbstractButton button;

        public PressButtonAction(AbstractButton button) {
            this.button = button;
        }
        public void actionPerformed(ActionEvent e) {
            button.doClick();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        rootPanel = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10), new java.awt.Dimension(32767, 10));
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10), new java.awt.Dimension(32767, 10));
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        playSentenceButton = new javax.swing.JButton();
        loopSentenceButton = new javax.swing.JToggleButton();
        filler7 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        sentenceWaveFormPanel = new skygge.UI.WaveFormPanel();
        jPanel11 = new javax.swing.JPanel();
        filler9 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        showLibraryButton = new javax.swing.JButton();
        showSentenceInfoButton = new javax.swing.JButton();
        filler6 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 8), new java.awt.Dimension(0, 8), new java.awt.Dimension(32767, 8));
        jSeparator1 = new javax.swing.JSeparator();
        filler5 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 8), new java.awt.Dimension(0, 8), new java.awt.Dimension(32767, 8));
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        playRecordingButton = new javax.swing.JButton();
        recordRecordingButton = new javax.swing.JToggleButton();
        filler10 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        recordingWaveFormPanel = new skygge.UI.WaveFormPanel();
        jPanel15 = new javax.swing.JPanel();
        filler11 = new javax.swing.Box.Filler(new java.awt.Dimension(60, 0), new java.awt.Dimension(60, 0), new java.awt.Dimension(60, 32767));
        jPanel16 = new javax.swing.JPanel();
        filler12 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767));
        statusBarLabel = new javax.swing.JLabel();
        filler13 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 5), new java.awt.Dimension(32767, 5));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Skygge");
        setLocation(new java.awt.Point(100, 100));
        setResizable(false);

        rootPanel.setLayout(new java.awt.BorderLayout());

        jPanel7.setLayout(new java.awt.BorderLayout());
        jPanel7.add(filler1, java.awt.BorderLayout.LINE_START);
        jPanel7.add(filler2, java.awt.BorderLayout.LINE_END);
        jPanel7.add(filler3, java.awt.BorderLayout.PAGE_END);
        jPanel7.add(filler4, java.awt.BorderLayout.PAGE_START);

        jPanel8.setLayout(new javax.swing.BoxLayout(jPanel8, javax.swing.BoxLayout.Y_AXIS));

        jPanel9.setMaximumSize(new java.awt.Dimension(2000, 2000));
        jPanel9.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanel9.setLayout(new java.awt.BorderLayout());

        jPanel2.setMinimumSize(new java.awt.Dimension(50, 0));
        jPanel2.setPreferredSize(new java.awt.Dimension(60, 100));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        playSentenceButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/media-playback-start.png"))); // NOI18N
        playSentenceButton.setToolTipText("Play the sentence. Hotkey: 'a'");
        playSentenceButton.setPreferredSize(new java.awt.Dimension(50, 50));
        playSentenceButton.setSize(new java.awt.Dimension(50, 50));
        playSentenceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playSentenceButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel2.add(playSentenceButton, gridBagConstraints);

        loopSentenceButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/view-refresh.png"))); // NOI18N
        loopSentenceButton.setToolTipText("Start/stop looping the sentence. Hotkey: 's'");
        loopSentenceButton.setPreferredSize(new java.awt.Dimension(50, 50));
        loopSentenceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loopSentenceButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        jPanel2.add(loopSentenceButton, gridBagConstraints);
        jPanel2.add(filler7, new java.awt.GridBagConstraints());

        jPanel9.add(jPanel2, java.awt.BorderLayout.LINE_START);

        sentenceWaveFormPanel.setMaximumSize(new java.awt.Dimension(32767, 100));
        sentenceWaveFormPanel.setMinimumSize(new java.awt.Dimension(600, 100));
        sentenceWaveFormPanel.setPreferredSize(new java.awt.Dimension(600, 100));

        javax.swing.GroupLayout sentenceWaveFormPanelLayout = new javax.swing.GroupLayout(sentenceWaveFormPanel);
        sentenceWaveFormPanel.setLayout(sentenceWaveFormPanelLayout);
        sentenceWaveFormPanelLayout.setHorizontalGroup(
            sentenceWaveFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 679, Short.MAX_VALUE)
        );
        sentenceWaveFormPanelLayout.setVerticalGroup(
            sentenceWaveFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 98, Short.MAX_VALUE)
        );

        jPanel9.add(sentenceWaveFormPanel, java.awt.BorderLayout.CENTER);

        jPanel11.setMinimumSize(new java.awt.Dimension(50, 0));
        jPanel11.setLayout(new java.awt.GridBagLayout());
        jPanel11.add(filler9, new java.awt.GridBagConstraints());

        showLibraryButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/music-library.png"))); // NOI18N
        showLibraryButton.setToolTipText("Open sentence library.");
        showLibraryButton.setPreferredSize(new java.awt.Dimension(50, 50));
        showLibraryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showLibraryButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel11.add(showLibraryButton, gridBagConstraints);

        showSentenceInfoButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/ibus-panel.png"))); // NOI18N
        showSentenceInfoButton.setToolTipText("Show sentence information.");
        showSentenceInfoButton.setActionCommand("I");
        showSentenceInfoButton.setPreferredSize(new java.awt.Dimension(50, 50));
        showSentenceInfoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showSentenceInfoButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        jPanel11.add(showSentenceInfoButton, gridBagConstraints);

        jPanel9.add(jPanel11, java.awt.BorderLayout.LINE_END);

        jPanel8.add(jPanel9);
        jPanel8.add(filler6);
        jPanel8.add(jSeparator1);
        jPanel8.add(filler5);

        jPanel12.setMaximumSize(new java.awt.Dimension(2000, 2000));
        jPanel12.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanel12.setLayout(new java.awt.BorderLayout());

        jPanel13.setMinimumSize(new java.awt.Dimension(50, 0));
        jPanel13.setLayout(new java.awt.GridBagLayout());

        playRecordingButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/media-playback-start.png"))); // NOI18N
        playRecordingButton.setToolTipText("Play the recording. Hotkey: 'd'");
        playRecordingButton.setEnabled(false);
        playRecordingButton.setPreferredSize(new java.awt.Dimension(50, 50));
        playRecordingButton.setSize(new java.awt.Dimension(50, 50));
        playRecordingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playRecordingButtonActionPerformed(evt);
            }
        });
        jPanel13.add(playRecordingButton, new java.awt.GridBagConstraints());

        recordRecordingButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/media-record.png"))); // NOI18N
        recordRecordingButton.setToolTipText("Record yourself. Hotkey: 'f'");
        recordRecordingButton.setMaximumSize(new java.awt.Dimension(50, 50));
        recordRecordingButton.setMinimumSize(new java.awt.Dimension(50, 50));
        recordRecordingButton.setPreferredSize(new java.awt.Dimension(50, 50));
        recordRecordingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recordRecordingButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        jPanel13.add(recordRecordingButton, gridBagConstraints);
        jPanel13.add(filler10, new java.awt.GridBagConstraints());

        jPanel12.add(jPanel13, java.awt.BorderLayout.LINE_START);

        recordingWaveFormPanel.setMaximumSize(new java.awt.Dimension(32767, 100));
        recordingWaveFormPanel.setMinimumSize(new java.awt.Dimension(600, 100));
        recordingWaveFormPanel.setPreferredSize(new java.awt.Dimension(600, 100));

        javax.swing.GroupLayout recordingWaveFormPanelLayout = new javax.swing.GroupLayout(recordingWaveFormPanel);
        recordingWaveFormPanel.setLayout(recordingWaveFormPanelLayout);
        recordingWaveFormPanelLayout.setHorizontalGroup(
            recordingWaveFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 679, Short.MAX_VALUE)
        );
        recordingWaveFormPanelLayout.setVerticalGroup(
            recordingWaveFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 98, Short.MAX_VALUE)
        );

        jPanel12.add(recordingWaveFormPanel, java.awt.BorderLayout.CENTER);

        jPanel15.setMinimumSize(new java.awt.Dimension(50, 0));
        jPanel15.setLayout(new java.awt.GridBagLayout());
        jPanel15.add(filler11, new java.awt.GridBagConstraints());

        jPanel12.add(jPanel15, java.awt.BorderLayout.LINE_END);

        jPanel8.add(jPanel12);

        jPanel7.add(jPanel8, java.awt.BorderLayout.CENTER);

        rootPanel.add(jPanel7, java.awt.BorderLayout.CENTER);

        jPanel16.setLayout(new java.awt.BorderLayout());
        jPanel16.add(filler12, java.awt.BorderLayout.WEST);

        statusBarLabel.setText(" ");
        jPanel16.add(statusBarLabel, java.awt.BorderLayout.CENTER);
        jPanel16.add(filler13, java.awt.BorderLayout.PAGE_END);

        rootPanel.add(jPanel16, java.awt.BorderLayout.SOUTH);

        getContentPane().add(rootPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void deselectToggleButtons() {
        deselectLoopSentenceButton();
        deselectRecordRecordingButton();
    }

    private void deselectLoopSentenceButton() {
        loopSentenceButton.setSelected(false);
    }

    private void deselectRecordRecordingButton() {
        recordRecordingButton.setSelected(false);
    }

    private void stopEverythingAndUpdateRecordedAudioData() {
        SoundDeviceManager.getInstance().stopEverything();

        if (isRecording) {
            recordingAudioData = SoundDeviceManager.getInstance().getRecordedAudioData();
            recordingWaveFormPanel.setAudioData(recordingAudioData);
            isRecording = false;
        }
    }


    private void playSentenceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playSentenceButtonActionPerformed
        deselectToggleButtons();
        stopEverythingAndUpdateRecordedAudioData();

        SoundDeviceManager.getInstance().startPlaying(sentenceAudioData);
    }//GEN-LAST:event_playSentenceButtonActionPerformed

    private void loopSentenceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loopSentenceButtonActionPerformed
        deselectRecordRecordingButton();
        stopEverythingAndUpdateRecordedAudioData();

        if (loopSentenceButton.isSelected())
            SoundDeviceManager.getInstance().startLooping(sentenceAudioData);
        else
            SoundDeviceManager.getInstance().stopEverything();
    }//GEN-LAST:event_loopSentenceButtonActionPerformed


    private void playRecordingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playRecordingButtonActionPerformed
        deselectToggleButtons();
        stopEverythingAndUpdateRecordedAudioData();

        if (recordingAudioData != null) {
            SoundDeviceManager.getInstance().startPlaying(recordingAudioData);
        }
    }//GEN-LAST:event_playRecordingButtonActionPerformed

    private void recordRecordingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recordRecordingButtonActionPerformed
        deselectLoopSentenceButton();

        if (!playRecordingButton.isEnabled()) {
            playRecordingButton.setEnabled(true);
        }

        if(recordRecordingButton.isSelected()){
            SoundDeviceManager.getInstance().startRecording();
            isRecording = true;
        } else {
            stopEverythingAndUpdateRecordedAudioData();
        }
    }//GEN-LAST:event_recordRecordingButtonActionPerformed



    private void showLibraryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showLibraryButtonActionPerformed
        sentenceLibraryFrame.setVisible(true);
    }//GEN-LAST:event_showLibraryButtonActionPerformed

    private void showSentenceInfoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showSentenceInfoButtonActionPerformed
        sentenceInfoFrame.setVisible(true);
    }//GEN-LAST:event_showSentenceInfoButtonActionPerformed


    public void loadSentence(Sentence sentence) {
        try {
            sentenceAudioData = Utils.loadUrlIntoByteArray(sentence.getUrl());
            for (int i = 0; i < sentenceAudioData.length; i++)
                sentenceAudioData[i] += 128;
            sentenceWaveFormPanel.setAudioData(sentenceAudioData);

            sentenceInfoFrame.setInformation(sentence.getInformation());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(-20);
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler10;
    private javax.swing.Box.Filler filler11;
    private javax.swing.Box.Filler filler12;
    private javax.swing.Box.Filler filler13;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.Box.Filler filler5;
    private javax.swing.Box.Filler filler6;
    private javax.swing.Box.Filler filler7;
    private javax.swing.Box.Filler filler9;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JToggleButton loopSentenceButton;
    private javax.swing.JButton playRecordingButton;
    private javax.swing.JButton playSentenceButton;
    private javax.swing.JToggleButton recordRecordingButton;
    private skygge.UI.WaveFormPanel recordingWaveFormPanel;
    private javax.swing.JPanel rootPanel;
    private skygge.UI.WaveFormPanel sentenceWaveFormPanel;
    private javax.swing.JButton showLibraryButton;
    private javax.swing.JButton showSentenceInfoButton;
    private javax.swing.JLabel statusBarLabel;
    // End of variables declaration//GEN-END:variables
}
