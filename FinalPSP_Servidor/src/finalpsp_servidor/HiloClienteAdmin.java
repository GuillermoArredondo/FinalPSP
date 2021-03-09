package finalpsp_servidor;

import Datos.Usuario;
import Utilities.Comunicacion;
import Utilities.Seguridad;
import controlador.Conexion;
import java.io.IOException;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;

/**
 *
 * @author Guille
 */
class HiloClienteAdmin extends Thread {

    private Socket cliente;
    private PublicKey clavePubAjena;
    private PrivateKey clavePrivPropia;
    private Conexion con;

    public HiloClienteAdmin(Socket cliente, PrivateKey clavePrivPropia, PublicKey clavePubAjena, Conexion con) {
        this.cliente = cliente;
        this.clavePrivPropia = clavePrivPropia;
        this.clavePubAjena = clavePubAjena;
        this.con = con;
    }

    @Override
    public void run() {

        try {

            System.out.println("HILO CLIENTE ADMIN");
            //enviar la lista de usuarios y la lista de admins
            enviarListaAdmins();
            enviarListaUsuarios();

            do {
                System.out.println("HILO CLIENTE ADMIN, ESPERANDO ORDEN");
                //espera la orden del cliente
                SealedObject so = (SealedObject) Comunicacion.recibirObjeto(cliente);
                int orden = (int) Seguridad.descifrar(clavePrivPropia, so);

                // orden 0 -> Activar un usuario
                //       1 -> Convertir en admin
                //       2 -> Eliminar un usuario
                //       3 -> Crear usuario
                //       4 -> Quitar priv de admin
                //       5 -> Eliminar un admin
                //       6 -> Crear admin
                
                switch (orden) {
                    case 0:
                        so = (SealedObject) Comunicacion.recibirObjeto(cliente);
                        String idUser = (String) Seguridad.descifrar(clavePrivPropia, so);
                        activarUser(idUser);
                        enviarListaUsuarios();
                        break;

                    case 1:
                        so = (SealedObject) Comunicacion.recibirObjeto(cliente);
                        idUser = (String) Seguridad.descifrar(clavePrivPropia, so);
                        convertirAdminUser(idUser);
                        enviarListaUsuarios();
                        enviarListaAdmins();
                        break;

                    case 2:
                        so = (SealedObject) Comunicacion.recibirObjeto(cliente);
                        idUser = (String) Seguridad.descifrar(clavePrivPropia, so);
                        eliminarUser(idUser);
                        enviarListaUsuarios();
                        enviarListaAdmins();
                        break;

                    case 3:
                        so = (SealedObject) Comunicacion.recibirObjeto(cliente);
                        Usuario u = (Usuario) Seguridad.descifrar(clavePrivPropia, so);
                        int res = 0;
                        if (usuarioOk(u)) {
                            registrarUsuario(u);
                            enviarRespuesta(res);
                            enviarListaUsuarios();
                            
                        } else {
                            res = 1;
                            enviarRespuesta(res);
                        }
                        break;
                        
                    case 4:
                        so = (SealedObject) Comunicacion.recibirObjeto(cliente);
                        idUser = (String) Seguridad.descifrar(clavePrivPropia, so);
                        quitarAdmin(idUser);
                        enviarListaAdmins();
                        break;
                        
                    case 5:
                        so = (SealedObject) Comunicacion.recibirObjeto(cliente);
                        idUser = (String) Seguridad.descifrar(clavePrivPropia, so);
                        eliminarUser(idUser);
                        enviarListaAdmins();
                        break;
                        
                    case 6:
                        so = (SealedObject) Comunicacion.recibirObjeto(cliente);
                        u = (Usuario) Seguridad.descifrar(clavePrivPropia, so);
                        res = 0;
                        if (usuarioOk(u)) {
                            registrarAdmin(u);
                            enviarRespuesta(res);
                            enviarListaAdmins();
                            enviarListaUsuarios();
                            
                        } else {
                            res = 1;
                            enviarRespuesta(res);
                        }
                        break;
                }

            } while (true);

        } catch (SQLException ex) {
        } catch (IOException | ClassNotFoundException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(HiloClienteAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void enviarListaAdmins() throws SQLException, IOException {

        ArrayList<Usuario> listaAdmin = obtenerAdmins();
        SealedObject so;
        //so = Seguridad.cifrar(clavePubAjena, listaAdmin);
        Comunicacion.enviarObjeto(cliente, listaAdmin);

    }

    private void enviarListaUsuarios() throws SQLException {

        try {
            ArrayList<Usuario> listaUsuarios = obtenerUsuarios();
            SealedObject so;
            //so = Seguridad.cifrar(clavePubAjena, listaUsuarios);
            Comunicacion.enviarObjeto(cliente, listaUsuarios);
        } catch (IOException ex) {
            Logger.getLogger(HiloClienteAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private ArrayList<Usuario> obtenerAdmins() throws SQLException {
        this.con.abrirConexion();
        ArrayList<Usuario> listaAdmins = this.con.obtenerAdmins();
        this.con.cerrarConexion();
        return listaAdmins;
    }

    private ArrayList<Usuario> obtenerUsuarios() throws SQLException {
        this.con.abrirConexion();
        ArrayList<Usuario> listaUsuarios = this.con.ObtenerUsus();
        this.con.cerrarConexion();
        return listaUsuarios;
    }

    private void activarUser(String idUser) throws SQLException {
        this.con.abrirConexion();
        this.con.activarUser(idUser);
        this.con.cerrarConexion();
    }

    private void convertirAdminUser(String idUser) throws SQLException {
        this.con.abrirConexion();
        this.con.convertirAdminUser(idUser);
        this.con.cerrarConexion();
    }

    private void eliminarUser(String idUser) throws SQLException {
        this.con.abrirConexion();
        this.con.eliminarUser(idUser);
        this.con.cerrarConexion();
    }

    private void enviarRespuesta(int res) {
        SealedObject so;
        try {

            so = Seguridad.cifrar(clavePubAjena, res);
            Comunicacion.enviarObjeto(cliente, so);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | IllegalBlockSizeException ex) {
        }

    }

    private void registrarUsuario(Usuario u) throws SQLException {
        this.con.abrirConexion();
        this.con.registrarUsuarioByAdmin(u);
        this.con.cerrarConexion();
    }

    private boolean usuarioOk(Usuario u) throws SQLException {
        boolean ok = true;
        this.con.abrirConexion();
        ArrayList<String> listaEmails = this.con.obtenerUsuarios();
        for (int i = 0; i < listaEmails.size(); i++) {
            if (u.getEmail().equals(listaEmails.get(i))) {
                ok = false;
            }
        }
        this.con.cerrarConexion();
        return ok;
    }

    private void quitarAdmin(String idUser) throws SQLException {
        this.con.abrirConexion();
        this.con.quitarAdmin(idUser);
        this.con.cerrarConexion();
    }

    private void registrarAdmin(Usuario u) {
        this.con.abrirConexion();
        this.con.registrarAdmin(u);
        this.con.cerrarConexion();
    }


}
