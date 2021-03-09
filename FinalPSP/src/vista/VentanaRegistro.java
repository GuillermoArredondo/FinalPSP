/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import Datos.Usuario;
import Utilities.Comunicacion;
import Utilities.Seguridad;
import finalpsp.Constantes;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Guille
 */
public class VentanaRegistro extends javax.swing.JFrame {

    private Socket servidor;
    private PrivateKey clavePrivPropia;
    private PublicKey clavePubAjena;
    private String path;
    private byte[] dataImage;
    private File selectedImage;
    
    public VentanaRegistro(PrivateKey clavePrivPropia, PublicKey clavePubAjena, Socket servidor) throws IOException {
        this.clavePrivPropia = clavePrivPropia;
        this.clavePubAjena = clavePubAjena;
        this.servidor = servidor;
        
        initComponents();
        setValuesToSpinner();
        setIconImage(Constantes.RUTA_IMA_USER);
        crearImagen();
    }
    
    private void setValuesPrefs() {
        lblArte.setText(sldArte.getValue()+"");
        lblDeporte.setText(sldDeporte.getValue()+"");
        lblPolitica.setText(sldPolitica.getValue()+"");
    }

    public void setIconImage(String ruta) {
        try {
            
            File im = new File(ruta);
            BufferedImage img = ImageIO.read(im);
            Image dimg = img.getScaledInstance(lblImaUser.getWidth(), lblImaUser.getHeight(),
            Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(dimg);
            lblImaUser.setIcon(imageIcon);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setValuesToSpinner() {
        SpinnerNumberModel spModel = new SpinnerNumberModel();
        spModel.setMaximum(99);
        spModel.setMinimum(18);
        spEdad.setModel(spModel);
        spEdad.setValue(18);
    }
    
    public boolean checkDatos(){
        boolean ok = true;
        if (txtNick.getText().isEmpty() ||
            txtEmail.getText().isEmpty() ||
            txtPass1.getPassword().length == 0 ||
            txtPass2.getPassword().length == 0) {
            ok = false;
        }
        return ok;
    }
    
    public boolean checkPrefs(){
        boolean ok = true;
        if (cbGen.getSelectedIndex() == 0 ||
            cbInteres.getSelectedIndex() == 0 ||
            cbRelacion.getSelectedIndex() == 0 ||
            cbTHijos.getSelectedIndex() == 0 ||
            cbQHijos.getSelectedIndex() == 0) {
            ok = false;
        }
        return ok;
    }
    
    public boolean checkPass(){
        boolean ok = false;
        if (Arrays.equals(txtPass1.getPassword(), txtPass2.getPassword())) {
            ok = true;
        }
        return ok;
    }
    
    public boolean checkEmail(){
        boolean ok = false;
        return ok;
    }
    
    private Usuario crearUsuario() throws NoSuchAlgorithmException {
        char th = 'N';
        char qh = 'N';
        if (cbTHijos.getSelectedIndex() == 1) {
            th = 'S';
        }
        if (cbQHijos.getSelectedIndex() == 1) {
            qh = 'S';
        }
        
        byte[] pass = resumirPwd();
        String pass2 = Seguridad.Hexadecimal(pass);
        
        return new Usuario(txtEmail.getText(), pass2,
                txtNick.getText(), (int) spEdad.getValue(), null,
                cbGen.getSelectedIndex(),
                cbInteres.getSelectedIndex(),
                cbRelacion.getSelectedIndex(),
                th, qh, sldDeporte.getValue(),
                sldArte.getValue(), sldPolitica.getValue(), 0);
    }

    private void crearImagen() throws IOException {
        
        if (selectedImage == null) {
            this.selectedImage = new File(Constantes.RUTA_IMA_USER);
        }else{
            BufferedImage bImage = ImageIO.read(selectedImage);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(bImage, "png", bos );
            this.dataImage = bos.toByteArray();
        }  
    }
    
    
    private byte[] resumirPwd() throws NoSuchAlgorithmException{
        char[] pass = txtPass1.getPassword();
        String passStr = new String(pass);
        
        return Seguridad.resumirPwd(passStr);
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
        pnlDatosUser = new javax.swing.JPanel();
        btnFoto = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        txtNick = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        spEdad = new javax.swing.JSpinner();
        txtPass1 = new javax.swing.JPasswordField();
        txtPass2 = new javax.swing.JPasswordField();
        lblImaUser = new javax.swing.JLabel();
        pnlPrefsUser = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        sldDeporte = new javax.swing.JSlider();
        cbRelacion = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lblDeporte = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        cbTHijos = new javax.swing.JComboBox<>();
        cbQHijos = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        lblArte = new javax.swing.JLabel();
        lblPolitica = new javax.swing.JLabel();
        sldPolitica = new javax.swing.JSlider();
        sldArte = new javax.swing.JSlider();
        btnRegistrar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        cbInteres = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        cbGen = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        btnFoto.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnFoto.setText("Elegir foto");
        btnFoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFotoActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Email:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Edad:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Nick:");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel7.setText("Datos");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Contraseña:");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Repetir contraseña:");

        txtNick.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtEmail.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        spEdad.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        spEdad.setValue(18);

        txtPass1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtPass2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lblImaUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Tipo de relación buscada:");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel2.setText("Preferencias");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setText("Interés por:");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setText("Interés por el arte:");

        sldDeporte.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sldDeporteStateChanged(evt);
            }
        });

        cbRelacion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecciona una", "Seria", "Esporádica" }));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setText("Pasión por el deporte:");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setText("Interés por la política:");

        lblDeporte.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblDeporte.setText("50");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel15.setText("¿Tienes hijos?");

        cbTHijos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecciona una", "Si", "No" }));

        cbQHijos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecciona una", "Si", "No" }));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel16.setText("¿Quieres hijos?");

        lblArte.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblArte.setText("50");

        lblPolitica.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblPolitica.setText("50");

        sldPolitica.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sldPoliticaStateChanged(evt);
            }
        });

        sldArte.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sldArteStateChanged(evt);
            }
        });

        btnRegistrar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnRegistrar.setText("Registrarse");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        btnCancelar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        cbInteres.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecciona una", "Hombres", "Mujeres", "Ambos" }));

        javax.swing.GroupLayout pnlPrefsUserLayout = new javax.swing.GroupLayout(pnlPrefsUser);
        pnlPrefsUser.setLayout(pnlPrefsUserLayout);
        pnlPrefsUserLayout.setHorizontalGroup(
            pnlPrefsUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPrefsUserLayout.createSequentialGroup()
                .addGroup(pnlPrefsUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPrefsUserLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(pnlPrefsUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(pnlPrefsUserLayout.createSequentialGroup()
                                .addGroup(pnlPrefsUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel10))
                                .addGap(18, 18, 18)
                                .addGroup(pnlPrefsUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cbRelacion, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cbInteres, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                                .addGroup(pnlPrefsUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(18, 18, 18)
                                .addGroup(pnlPrefsUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbQHijos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbTHijos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(pnlPrefsUserLayout.createSequentialGroup()
                        .addGap(94, 94, 94)
                        .addGroup(pnlPrefsUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlPrefsUserLayout.createSequentialGroup()
                                .addComponent(btnRegistrar)
                                .addGap(37, 37, 37)
                                .addComponent(btnCancelar))
                            .addGroup(pnlPrefsUserLayout.createSequentialGroup()
                                .addGroup(pnlPrefsUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel13))
                                .addGap(33, 33, 33)
                                .addGroup(pnlPrefsUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(sldDeporte, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(sldPolitica, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(sldArte, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(33, 33, 33)
                        .addGroup(pnlPrefsUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPolitica)
                            .addComponent(lblArte)
                            .addComponent(lblDeporte))))
                .addContainerGap(85, Short.MAX_VALUE))
        );
        pnlPrefsUserLayout.setVerticalGroup(
            pnlPrefsUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPrefsUserLayout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(23, 23, 23)
                .addGroup(pnlPrefsUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel15)
                    .addComponent(cbTHijos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbInteres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlPrefsUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cbRelacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbQHijos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addGap(35, 35, 35)
                .addGroup(pnlPrefsUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPrefsUserLayout.createSequentialGroup()
                        .addGroup(pnlPrefsUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDeporte, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12))
                        .addGap(18, 18, 18)
                        .addGroup(pnlPrefsUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(lblArte, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(pnlPrefsUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(lblPolitica, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(pnlPrefsUserLayout.createSequentialGroup()
                        .addComponent(sldDeporte, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(pnlPrefsUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlPrefsUserLayout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addComponent(sldPolitica, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(sldArte, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                .addGroup(pnlPrefsUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegistrar)
                    .addComponent(btnCancelar))
                .addGap(37, 37, 37))
        );

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setText("Género:");

        cbGen.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecciona una", "Hombre", "Mujer" }));

        javax.swing.GroupLayout pnlDatosUserLayout = new javax.swing.GroupLayout(pnlDatosUser);
        pnlDatosUser.setLayout(pnlDatosUserLayout);
        pnlDatosUserLayout.setHorizontalGroup(
            pnlDatosUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDatosUserLayout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addGroup(pnlDatosUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDatosUserLayout.createSequentialGroup()
                        .addComponent(btnFoto)
                        .addGap(81, 81, 81))
                    .addGroup(pnlDatosUserLayout.createSequentialGroup()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 589, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDatosUserLayout.createSequentialGroup()
                        .addGroup(pnlDatosUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlDatosUserLayout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(32, 32, 32)
                                .addComponent(txtNick, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlDatosUserLayout.createSequentialGroup()
                                .addGroup(pnlDatosUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel14))
                                .addGroup(pnlDatosUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlDatosUserLayout.createSequentialGroup()
                                        .addGap(33, 33, 33)
                                        .addComponent(cbGen, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(spEdad, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDatosUserLayout.createSequentialGroup()
                                        .addGap(32, 32, 32)
                                        .addGroup(pnlDatosUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtPass2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtPass1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtEmail, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addGap(40, 40, 40)
                        .addComponent(lblImaUser, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDatosUserLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlPrefsUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pnlDatosUserLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel7)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnlDatosUserLayout.setVerticalGroup(
            pnlDatosUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDatosUserLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel7)
                .addGap(25, 25, 25)
                .addGroup(pnlDatosUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDatosUserLayout.createSequentialGroup()
                        .addGroup(pnlDatosUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtNick, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlDatosUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlDatosUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtPass1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lblImaUser, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlDatosUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtPass2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlDatosUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spEdad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(btnFoto)
                    .addComponent(jLabel14)
                    .addComponent(cbGen, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlPrefsUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlDatosUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlDatosUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sldDeporteStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sldDeporteStateChanged
        setValuesPrefs();
    }//GEN-LAST:event_sldDeporteStateChanged

    private void sldArteStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sldArteStateChanged
        setValuesPrefs();
    }//GEN-LAST:event_sldArteStateChanged

    private void sldPoliticaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sldPoliticaStateChanged
        setValuesPrefs();
    }//GEN-LAST:event_sldPoliticaStateChanged

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnFotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFotoActionPerformed
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg","png");
        jFileChooser1.addChoosableFileFilter(filter);
        int result = jFileChooser1.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            this.selectedImage = jFileChooser1.getSelectedFile();
            this.path = this.selectedImage.getAbsolutePath();
            setIconImage(path);
        }
    }//GEN-LAST:event_btnFotoActionPerformed

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        
        if (checkDatos()) {
            if (checkPass()) {
                if (checkPrefs()) {
                    try {
                        
                        //envio la orden de registro al servidor
                        int orden = 0;
                        SealedObject so =Seguridad.cifrar(this.clavePubAjena, orden);
                        Comunicacion.enviarObjeto(servidor, so);
                        System.out.println("ORDEN REGISTRO OK");
                        crearImagen();
                        
                        //creo el usuario y lo envio
                        Usuario u = crearUsuario();
                        so = Seguridad.cifrar(this.clavePubAjena, u);
                        Comunicacion.enviarObjeto(servidor,so);
                        System.out.println("ENVIADO USUARIO OK");
                        
                        //recibo el codigo de respuesta del servidor
                        so = (SealedObject) Comunicacion.recibirObjeto(servidor);
                        int res = (int) Seguridad.descifrar(clavePrivPropia, so);
                        
                        //Exito
                        if (res == 0) {
                            JOptionPane.showMessageDialog(null,"Usuario registrado con exito");
                            this.dispose();
                        
                        //Email repetido
                        }else {
                            JOptionPane.showMessageDialog(null,"El email introducido ya existe"); 
                        }
                        
                    } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | IllegalBlockSizeException | ClassNotFoundException | BadPaddingException ex) {
                        Logger.getLogger(VentanaRegistro.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Debes marcar todas las preferencias");
                }
            }else{
                JOptionPane.showMessageDialog(null,"Las contraseñas no son iguales");
            }
        }else{
            JOptionPane.showMessageDialog(null,"Debes rellenar todos los datos de usuario");
        }
    }//GEN-LAST:event_btnRegistrarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnFoto;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JComboBox<String> cbGen;
    private javax.swing.JComboBox<String> cbInteres;
    private javax.swing.JComboBox<String> cbQHijos;
    private javax.swing.JComboBox<String> cbRelacion;
    private javax.swing.JComboBox<String> cbTHijos;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblArte;
    private javax.swing.JLabel lblDeporte;
    private javax.swing.JLabel lblImaUser;
    private javax.swing.JLabel lblPolitica;
    private javax.swing.JPanel pnlDatosUser;
    private javax.swing.JPanel pnlPrefsUser;
    private javax.swing.JSlider sldArte;
    private javax.swing.JSlider sldDeporte;
    private javax.swing.JSlider sldPolitica;
    private javax.swing.JSpinner spEdad;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNick;
    private javax.swing.JPasswordField txtPass1;
    private javax.swing.JPasswordField txtPass2;
    // End of variables declaration//GEN-END:variables

}
