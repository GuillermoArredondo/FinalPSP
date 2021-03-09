/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;

/**
 *
 * @author Guille
 */
public class HiloClientes extends Thread {

    private Socket cliente;
    private PublicKey clavePubAjena;
    private PublicKey clavePubPropia;
    private PrivateKey clavePrivPropia;
    private Conexion con;

    public HiloClientes(Socket cliente, PublicKey clavePubPropia, PrivateKey clavePrivPropia) {
        this.cliente = cliente;
        this.clavePubPropia = clavePubPropia;
        this.clavePrivPropia = clavePrivPropia;
        this.con = new Conexion();
    }

    @Override
    public void run() {

        try {
            //recibe la clave pub del cliente
            clavePubAjena = (PublicKey) Comunicacion.recibirObjeto(cliente);

            //envia la clave pub del servidor
            Comunicacion.enviarObjeto(cliente, clavePubPropia);

            System.out.println("ok");
            boolean activo = true;
            do {

                //recibe orden
                SealedObject so = (SealedObject) Comunicacion.recibirObjeto(cliente);
                int orden = (int) Seguridad.descifrar(clavePrivPropia, so);

                //registrar un usuario -> 0
                if (orden == 0) {

                    System.out.println("ORDEN REGISTRO");
                    //recibe el usuario a registrar
                    so = (SealedObject) Comunicacion.recibirObjeto(cliente);
                    Usuario u = (Usuario) Seguridad.descifrar(clavePrivPropia, so);
                    System.out.println("RECIBIDO USUARIO OK" + u.getNick());

                    int res = 0;
                    if (usuarioOk(u)) {
                        registrarUsuario(u);
                        enviarRespuesta(res);
                    } else {
                        res = 1;
                        enviarRespuesta(res);
                    }
                
                //loguear usuario -> 1
                }else if (orden == 1) {
                    
                    System.out.println("ORDEN LOGIN");
                    //recibe el usuario a loguear
                    so = (SealedObject) Comunicacion.recibirObjeto(cliente);
                    Usuario u = (Usuario) Seguridad.descifrar(clavePrivPropia, so);
                    System.out.println("SERVIDOR Recibo Usuario");
                    
                    //La respuesta: sera 0 si el email y pass coinciden (Admin)
                    //              sera 1 si el email y pass coinciden (Usuario Activo)
                    //              sera 2 si el email y pass coinciden (Usuario No Activo)
                    //              sera 3 si la pass no coincide
                    //              sera 4 si no existe el email
                    int res = 0;
                    if (!usuarioOk(u)) {
                        if (usuarioPass(u)) {
                            System.out.println("SERVIDOR Recibo Usuario" + u.getNick());
                            //obtengo el id del usuario
                            String idUser = obtenerIdUser(u);
                            
                            //compruebo si ese usuario es admin
                            if (checkAdmin(idUser)) {
                                
                                //envio la orden
                                enviarRespuesta(res);
                                
                                //obtengo el usu admin y lo envio
                                Usuario usu = obtenerUsuarioAdmin(idUser);
                                so = Seguridad.cifrar(clavePubAjena, usu);
                                Comunicacion.enviarObjeto(cliente, so);
                                HiloClienteAdmin hca = new HiloClienteAdmin(cliente, clavePrivPropia, clavePubAjena, con);
                                hca.start();
                                activo = false;
                                
                                
                            }else{
                                //si no es admin, compruebo que este activo
                                Usuario usu = obtenerUsuario(idUser);
                                
                                if (usu.getActivo() == 0) {
                                    //si no esta activo envio la respuesta
                                    res = 2;
                                    enviarRespuesta(res);
                                    
                                }else{
                                    //si esta activo, envio la respuesta y el usuario
                                    res = 1;
                                    enviarRespuesta(res);
                                    so = Seguridad.cifrar(clavePubAjena, usu);
                                    Comunicacion.enviarObjeto(cliente, so);
                                    HiloClienteUsuario hcu = new HiloClienteUsuario(cliente, clavePrivPropia, clavePubAjena, con);
                                    hcu.start();
                                    activo = false;
                                }
                            }
                            
                        }else{
                            res = 3;
                            enviarRespuesta(res);
                        }
                        
                    }else{
                        res = 4;
                        enviarRespuesta(res);
                    }
                    
                }

            } while (activo);

        } catch (IOException ex) {
        } catch (ClassNotFoundException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | SQLException ex) {
        }

    }
    
    private void enviarRespuesta(int res){
        SealedObject so;
        try {
            
            so = Seguridad.cifrar(clavePubAjena, res);
            Comunicacion.enviarObjeto(cliente, so);
            
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | IllegalBlockSizeException ex) {
        }
        
    }
    

    private void registrarUsuario(Usuario u) throws SQLException {
        this.con.abrirConexion();
        this.con.registrarUsuario(u);
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

    private boolean usuarioPass(Usuario u) throws SQLException {
        boolean ok = false;
        String pass = u.getPwd();
        this.con.abrirConexion();
        String pass2 = this.con.obtenerPass(u.getEmail());
        if (pass.equals(pass2)) {
            ok = true;
        }
        this.con.cerrarConexion();
        return ok;
    }

    private Usuario obtenerUsuarioAdmin(String idUser) throws SQLException {
        this.con.abrirConexion();
        Usuario u = this.con.obtenerUsuAdmin(idUser);
        this.con.cerrarConexion();
        return u;
    }

    private boolean checkAdmin(String idUser) throws SQLException {
        boolean isAdmin = false;
        this.con.abrirConexion();
        ArrayList<String> listaIdsAdmins = this.con.obtenerUsuariosAdmins();
        
        for (int i = 0; i < listaIdsAdmins.size(); i++) {
            if (idUser.equals(listaIdsAdmins.get(i))) {
               isAdmin = true; 
            }
        }
      
        this.con.cerrarConexion();
        return isAdmin;  
    }

    private String obtenerIdUser(Usuario u) throws SQLException {
        String idUser = "";
        this.con.abrirConexion();
        idUser = this.con.obtenerIdUser(u.getEmail());
        this.con.cerrarConexion();
        return idUser;
    }

    private Usuario obtenerUsuario(String idUser) throws SQLException {
        this.con.abrirConexion();
        Usuario u = this.con.obtenerUsuario(idUser);
        this.con.cerrarConexion();
        return u;
    }

}
