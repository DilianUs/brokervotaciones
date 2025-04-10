/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Vista;

import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author braul
 */
public class VistaVotacion extends javax.swing.JFrame {

    /**
     * Creates new form VistaVotacion
     */
    public VistaVotacion() {
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

        labelTitulo = new javax.swing.JLabel();
        botonP1 = new javax.swing.JButton();
        botonP3 = new javax.swing.JButton();
        botonP2 = new javax.swing.JButton();
        labelTotalNaranja = new javax.swing.JLabel();
        labelTotalNaranja1 = new javax.swing.JLabel();
        labelTotalNaranja2 = new javax.swing.JLabel();
        textoTotalP1 = new javax.swing.JLabel();
        textoTotalP2 = new javax.swing.JLabel();
        textoTotalP3 = new javax.swing.JLabel();
        graficasbtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        labelTitulo.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        labelTitulo.setForeground(new java.awt.Color(0, 0, 0));
        labelTitulo.setText("¡Vota por un producto!");

        botonP1.setText("Producto1");
        botonP1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonP1ActionPerformed(evt);
            }
        });

        botonP3.setText("Producto3");
        botonP3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonP3ActionPerformed(evt);
            }
        });

        botonP2.setText("Producto2");
        botonP2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonP2ActionPerformed(evt);
            }
        });

        labelTotalNaranja.setForeground(new java.awt.Color(0, 0, 0));
        labelTotalNaranja.setText("Total:");

        labelTotalNaranja1.setForeground(new java.awt.Color(0, 0, 0));
        labelTotalNaranja1.setText("Total:");

        labelTotalNaranja2.setForeground(new java.awt.Color(0, 0, 0));
        labelTotalNaranja2.setText("Total:");

        textoTotalP1.setText("jLabel1");

        textoTotalP2.setText("jLabel1");

        textoTotalP3.setText("jLabel1");

        graficasbtn.setText("Ver Graficas");
        graficasbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                graficasbtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(botonP3)
                            .addComponent(botonP1)
                            .addComponent(botonP2))
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(labelTotalNaranja)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(textoTotalP1))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(labelTotalNaranja1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(textoTotalP2))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(labelTotalNaranja2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(textoTotalP3))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(labelTitulo))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(153, 153, 153)
                        .addComponent(graficasbtn)))
                .addContainerGap(71, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelTitulo)
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonP1)
                    .addComponent(labelTotalNaranja)
                    .addComponent(textoTotalP1))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelTotalNaranja1)
                        .addComponent(textoTotalP2))
                    .addComponent(botonP2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelTotalNaranja2)
                        .addComponent(textoTotalP3))
                    .addComponent(botonP3))
                .addGap(18, 18, 18)
                .addComponent(graficasbtn)
                .addGap(22, 22, 22))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonP1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonP1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_botonP1ActionPerformed

    private void botonP3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonP3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_botonP3ActionPerformed

    private void botonP2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonP2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_botonP2ActionPerformed

    private void graficasbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_graficasbtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_graficasbtnActionPerformed

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
            java.util.logging.Logger.getLogger(VistaVotacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaVotacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaVotacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaVotacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VistaVotacion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton botonP1;
    public javax.swing.JButton botonP2;
    public javax.swing.JButton botonP3;
    private javax.swing.JButton graficasbtn;
    private javax.swing.JLabel labelTitulo;
    private javax.swing.JLabel labelTotalNaranja;
    private javax.swing.JLabel labelTotalNaranja1;
    private javax.swing.JLabel labelTotalNaranja2;
    private javax.swing.JLabel textoTotalP1;
    private javax.swing.JLabel textoTotalP2;
    private javax.swing.JLabel textoTotalP3;
    // End of variables declaration//GEN-END:variables
    //getter y setter de los botones 
    public JButton getButton1(){
        return  botonP1;         
    }
    
    public void setButton1(JButton botonP1){
        this.botonP1 = botonP1;         
    }
    
    public JButton getButton2(){
        return  botonP2;         
    }
    
    public void setButton2(JButton botonP2){
        this.botonP2 = botonP2;         
    }
    
    public JButton getButton3(){
        return  botonP3;         
    }
    
    public void setButton3(JButton botonP3){
        this.botonP3 = botonP3;         
    }
    
    public JLabel getTextoTotalP1(){
        return  textoTotalP1;         
    }
    
    public void setTextoTotalP1(JLabel textoTotalP1){
        this.textoTotalP1 = textoTotalP1;         
    }
    
    public JLabel getTextoTotalP2(){
        return  textoTotalP2;         
    }
    
    public void setTextoTotalP2(JLabel textoTotalP2){
        this.textoTotalP2 = textoTotalP2;         
    }
    
    public JLabel getTextoTotalP3(){
        return  textoTotalP3;         
    }
    
    public void setTextoTotalP3(JLabel textoTotalP3){
        this.textoTotalP3 = textoTotalP3;         
    }
    
    public void setGraficasbtn(JButton graficasbtn){
        this.graficasbtn = graficasbtn;         
    }
    
    public JButton getGraficasbtn(){
        return  graficasbtn;         
    }
    

}
