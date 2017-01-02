/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.uniza.fri.pds.spotreba.energie.gui;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImagePanel extends javax.swing.JPanel {

    private BufferedImage bi;
    private List<PropertyChangeListener> pcs;

    public void setImage(BufferedImage bi) {
        this.bi = bi;
        jLabel1.setIcon(new ImageIcon(bi));
        BufferedImage oldImage = bi;
        pcs.forEach((listener) -> {
            listener.propertyChange(new PropertyChangeEvent(this, "image", oldImage, bi));
        });
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        if (pcs == null) {
            pcs = new LinkedList<>();
        }
        this.pcs.add(listener);
        super.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.remove(listener);
        super.removePropertyChangeListener(listener);
    }

    /**
     * Creates new form ImagePanel
     */
    public ImagePanel() {

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

        fileChoser = new javax.swing.JFileChooser();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        java.awt.GridBagLayout layout = new java.awt.GridBagLayout();
        layout.columnWeights = new double[] {1.0};
        layout.rowWeights = new double[] {1.0};
        setLayout(layout);
        add(jLabel1, new java.awt.GridBagConstraints());

        jButton1.setText("...");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1, new java.awt.GridBagConstraints());
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        fileChoser.showOpenDialog(this.getRootPane());
        File selectedFile = fileChoser.getSelectedFile();
        if (selectedFile != null) {
            try {
                BufferedImage image = ImageIO.read(selectedFile);
                int oldWidth = image.getWidth();
                int oldHeight = image.getHeight();
                double aspect = oldWidth / ((double) oldHeight);
                int newHeight = 100;
                int newWidth = (int) Math.floor(newHeight * aspect);

                BufferedImage resized = new BufferedImage(newWidth, newHeight, image.getType());
                Graphics2D g = resized.createGraphics();
                g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                        RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g.drawImage(image, 0, 0, newWidth, newHeight, 0, 0, image.getWidth(),
                        image.getHeight(), null);
                g.dispose();
                setImage(resized);
            } catch (IOException ex) {
                Logger.getLogger(ImagePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser fileChoser;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
