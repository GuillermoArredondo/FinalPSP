
package finalpsp_servidor;

import Datos.Chat;
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
public class HiloChat extends Thread{

    private ArrayList<String> idsUsers;
    private Socket cliente;
    private PublicKey clavePubAjena;
    private PrivateKey clavePrivPropia;
    private Conexion con;

    public HiloChat(ArrayList<String> idsUsers, Socket cliente, PrivateKey clavePrivPropia, PublicKey clavePubAjena, Conexion con) {
        this.idsUsers = idsUsers;
        this.cliente = cliente;
        this.clavePrivPropia = clavePrivPropia;
        this.clavePubAjena = clavePubAjena;
        this.con = con;
    }
    
    @Override
    public void run(){
        
        try {
            ArrayList<String> idsUsers2 = new ArrayList<>();
            idsUsers2.add(idsUsers.get(1));
            idsUsers2.add(idsUsers.get(0));
            
            //comprobar si ya existe el chat entre los users
            if (checkChat(idsUsers)) {
                //si existe envio el chat al usuario
                Chat c = obtenerChat(idsUsers);
                enviarChat(c);
                
            }else if(checkChat(idsUsers2)){
                this.idsUsers = idsUsers2;
                Chat c = obtenerChat(idsUsers);
                enviarChat(c);        
                        
                
            //si no existe creo un nuevo chat y lo envio vacio
            }else{
                crearChat(idsUsers);
                Chat c = obtenerChat(idsUsers);
                enviarChat(c);
            }
            
            
            
            boolean activo = true;
            do {
                
                //espero la orden
                SealedObject so = (SealedObject) Comunicacion.recibirObjeto(cliente);
                int orden = (int) Seguridad.descifrar(clavePrivPropia, so);
                
                // orden 0 -> Enviar mensage
                //       1 -> Refrescar
                //       2 -> salir
                
                switch (orden){
                    
                    case 0:
                        //recibo msg
                        so = (SealedObject) Comunicacion.recibirObjeto(cliente);
                        String msg = (String) Seguridad.descifrar(clavePrivPropia, so);
                        
                        //recibo mi id
                        so = (SealedObject) Comunicacion.recibirObjeto(cliente);
                        String miId = (String) Seguridad.descifrar(clavePrivPropia, so);
                        
                        enviarMensage(idsUsers, msg, miId);
                        Chat c = obtenerChat(idsUsers);
                        enviarChat(c);
                        
                        break;
                        
                    case 1:
                        c = obtenerChat(idsUsers);
                        enviarChat(c);
                        break;
                        
                    case 2:
                        activo = false;
                        so = (SealedObject) Comunicacion.recibirObjeto(cliente);
                        msg = (String) Seguridad.descifrar(clavePrivPropia, so);
                        
                        HiloClienteUsuario hcu = new HiloClienteUsuario(msg, cliente, clavePrivPropia, clavePubAjena, con);
                        hcu.start();
                        break;
                    
                }
                
                
            } while (activo);
            
            
            
            
            
        } catch (IOException | ClassNotFoundException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | SQLException ex) {
            Logger.getLogger(HiloChat.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private boolean checkChat(ArrayList<String> idsUsers) {
        boolean exists = false;
        this.con.abrirConexion();
        if (this.con.existeChat(idsUsers)) {
            exists = true;
        }
        this.con.cerrarConexion();
        return exists;
    }

    private Chat obtenerChat(ArrayList<String> idsUsers) throws SQLException {
        this.con.abrirConexion();
        Chat c = this.con.ObtenerChat(idsUsers);
        this.con.cerrarConexion();
        return c;
    }

    private void crearChat(ArrayList<String> idsUsers) throws SQLException {
        this.con.abrirConexion();
        this.con.crearChat(idsUsers);
        this.con.cerrarConexion();
    }

    private void enviarChat(Chat c) throws IOException {
        Comunicacion.enviarObjeto(cliente, c);
    }

    private void enviarMensage(ArrayList<String> idsUsers, String msg, String miId) throws SQLException {
        this.con.abrirConexion();
        this.con.enviarMensage(idsUsers, msg, miId);
        this.con.cerrarConexion();
    }
    
    
    
}
