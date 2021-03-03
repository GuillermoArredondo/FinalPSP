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
                        so = Seguridad.cifrar(clavePubAjena, res);
                        Comunicacion.enviarObjeto(cliente, so);
                    } else {
                        res = 1;
                        so = Seguridad.cifrar(clavePubAjena, res);
                        Comunicacion.enviarObjeto(cliente, so);
                    }
                
                //loguear usuario -> 1
                }else if (orden == 1) {
                    
                    System.out.println("ORDEN LOGIN");
                    
                    
                }

            } while (true);

        } catch (IOException ex) {
        } catch (ClassNotFoundException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | SQLException ex) {
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

}
