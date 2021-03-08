/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import Datos.Usuario;
import Utilities.Comunicacion;
import Utilities.Seguridad;
import java.io.IOException;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Guille
 */
public class VentanaModAdmin extends javax.swing.JFrame {

    private Socket servidor;
    private PrivateKey clavePrivPropia;
    private PublicKey clavePubAjena;
    private JTable tableUsers;
    private ArrayList<Usuario> listaUsuarios;

    public VentanaModAdmin(Socket servidor, PrivateKey clavePrivPropia, PublicKey clavePubAjena, JTable tableUsers) {
        this.servidor = servidor;
        this.clavePrivPropia = clavePrivPropia;
        this.clavePubAjena = clavePubAjena;
        this.tableUsers = tableUsers;
        initComponents();
        setValuesToSpinner();
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        txtNick = new javax.swing.JTextField();
        ckActivo = new javax.swing.JCheckBox();
        spEdad = new javax.swing.JSpinner();
        btnAceptar = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();
        lblTitulo = new javax.swing.JLabel();
        txtPassAd = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Email:");

        jLabel2.setText("Contraseņa:");

        jLabel3.setText("Nick:");

        jLabel4.setText("Edad:");

        jLabel5.setText("Activo");

        btnAceptar.setText("Aceptar");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        btnVolver.setText("Volver");
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblTitulo.setText("Crear usuario");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ckActivo)
                    .addComponent(spEdad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEmail)
                    .addComponent(txtNick)
                    .addComponent(txtPassAd, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(120, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(lblTitulo)
                        .addGap(136, 136, 136))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnAceptar)
                        .addGap(18, 18, 18)
                        .addComponent(btnVolver)
                        .addGap(93, 93, 93))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtPassAd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNick, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(spEdad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(ckActivo))
                .addGap(52, 52, 52)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAceptar)
                    .addComponent(btnVolver))
                .addGap(38, 38, 38))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed

        if (checkVacios()) {

            try {

                //envio la orden de registro al servidor    
                int orden = 3;
                SealedObject so;
                so = Seguridad.cifrar(this.clavePubAjena, orden);
                Comunicacion.enviarObjeto(servidor, so);

                //creo el usuario y lo envio
                Usuario u = crearUsuario();
                so = Seguridad.cifrar(this.clavePubAjena, u);
                Comunicacion.enviarObjeto(servidor, so);

                //recibo el codigo de respuesta del servidor
                so = (SealedObject) Comunicacion.recibirObjeto(servidor);
                int res = (int) Seguridad.descifrar(clavePrivPropia, so);

                //Exito
                if (res == 0) {

                    JOptionPane.showMessageDialog(null, "Usuario registrado con exito");
                    rellenarTablaUsuarios();
                    this.dispose();

                    //Email repetido
                } else {
                    JOptionPane.showMessageDialog(null, "El email introducido ya existe");
                }

            } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | IllegalBlockSizeException | ClassNotFoundException | BadPaddingException ex) {
                Logger.getLogger(VentanaModAdmin.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnVolverActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnVolver;
    private javax.swing.JCheckBox ckActivo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JSpinner spEdad;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNick;
    private javax.swing.JPasswordField txtPassAd;
    // End of variables declaration//GEN-END:variables

    private void setValuesToSpinner() {
        SpinnerNumberModel spModel = new SpinnerNumberModel();
        spModel.setMaximum(99);
        spModel.setMinimum(18);
        spEdad.setModel(spModel);
        spEdad.setValue(18);

    }

    private boolean checkVacios() {
        boolean ok = true;
        if (txtNick.getText().isEmpty()
                || txtEmail.getText().isEmpty()
                || txtPassAd.getText().isEmpty()) {
            ok = false;
        }
        return ok;
    }

    private Usuario crearUsuario() throws NoSuchAlgorithmException {
        byte[] pass = resumirPwd();
        String pass2 = Seguridad.Hexadecimal(pass);

        int activo = 0;
        if (ckActivo.isSelected()) {
            activo = 1;
        }

        Usuario u = new Usuario();
        u.setId(u.generateId());
        u.setEmail(txtEmail.getText());
        u.setPwd(pass2);
        u.setNick(txtNick.getText());
        u.setEdad((int) spEdad.getValue());
        u.setActivo(activo);

        return u;
    }

    private byte[] resumirPwd() throws NoSuchAlgorithmException {
        char[] pass = txtPassAd.getPassword();
        String passStr = new String(pass);

        return Seguridad.resumirPwd(passStr);
    }

    private void rellenarTablaUsuarios() throws IOException, ClassNotFoundException {

        try {

            SealedObject so = (SealedObject) Comunicacion.recibirObjeto(servidor);
            this.listaUsuarios = (ArrayList<Usuario>) Seguridad.descifrar(clavePrivPropia, so);
            VentanaAdmin.listaUsuarios = this.listaUsuarios;

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(VentanaAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }

        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("ID");
        modelo.addColumn("Email");
        modelo.addColumn("Nick");
        modelo.addColumn("Edad");
        modelo.addColumn("Activo");

        Object[] o = new Object[5];
        for (int i = 0; i < listaUsuarios.size(); i++) {
            o[0] = listaUsuarios.get(i).getId();
            o[1] = listaUsuarios.get(i).getEmail();
            o[2] = listaUsuarios.get(i).getNick();
            o[3] = listaUsuarios.get(i).getEdad();
            if (listaUsuarios.get(i).getActivo() == 0) {
                o[4] = false;
            } else {
                o[4] = true;
            }

            modelo.addRow(o);
        }
        tableUsers.setModel(modelo);
        tableUsers.setDefaultEditor(Object.class, null);

    }

}
