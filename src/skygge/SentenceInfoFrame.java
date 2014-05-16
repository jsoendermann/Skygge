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
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        labelChinese = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        labelPinyin = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        labelEnglish = new javax.swing.JLabel();

        setTitle("Sentence Info");
        getContentPane().setLayout(new java.awt.GridLayout(3, 2));

        jLabel1.setText("Chinese:");
        getContentPane().add(jLabel1);

        labelChinese.setText("燕子的尾巴像剪刀。");
        getContentPane().add(labelChinese);

        jLabel3.setText("Pinyin:");
        jLabel3.setAlignmentY(0.2F);
        getContentPane().add(jLabel3);

        labelPinyin.setText("Yan4zi de wei3ba xiang4 jian3dao1.");
        getContentPane().add(labelPinyin);

        jLabel5.setText("English:");
        jLabel5.setAlignmentY(0.2F);
        getContentPane().add(jLabel5);

        labelEnglish.setText("The tail of the sparrow looks like a pair of scissors.");
        getContentPane().add(labelEnglish);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SentenceInfoFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SentenceInfoFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SentenceInfoFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SentenceInfoFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SentenceInfoFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel labelChinese;
    private javax.swing.JLabel labelEnglish;
    private javax.swing.JLabel labelPinyin;
    // End of variables declaration//GEN-END:variables
}
