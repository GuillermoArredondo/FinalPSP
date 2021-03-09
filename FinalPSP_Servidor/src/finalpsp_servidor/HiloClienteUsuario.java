
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
class HiloClienteUsuario extends Thread{
    
    private Socket cliente;
    private PublicKey clavePubAjena;
    private PrivateKey clavePrivPropia;
    private Conexion con;
    
    public HiloClienteUsuario(Socket cliente, PrivateKey clavePrivPropia, PublicKey clavePubAjena, Conexion con) {
        this.cliente = cliente;
        this.clavePrivPropia = clavePrivPropia;
        this.clavePubAjena = clavePubAjena;
        this.con = con;
    }
    
    
    @Override
    public void run(){
        
        //enviar lista de amigos
        
        //enviar lista de usuarios
        
        do {
            
            try {
                
                //recibo orden del cliente
                SealedObject so = (SealedObject) Comunicacion.recibirObjeto(cliente);
                int orden = (int) Seguridad.descifrar(clavePrivPropia, so);
                
                //orden 0 -> Modificar preferencias del usuario
                switch (orden){
                    case 0:
                        //recibo el usu a modificar
                        so = (SealedObject) Comunicacion.recibirObjeto(cliente);
                        Usuario u = (Usuario) Seguridad.descifrar(clavePrivPropia, so);
                        modificarPreferencias(u);
                        //enviar nuevo usuario
                        Usuario newU = obtenerUsuario(u.getId());
                        so = Seguridad.cifrar(clavePubAjena, newU);
                        Comunicacion.enviarObjeto(cliente, so);
                         
                        break;
                        
                    case 1:
                        break;
                        
                    case 2:
                        break;
                        
                    case 3:
                        break;
                    
                }
                
                
                
            } catch (IOException | ClassNotFoundException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | SQLException ex) {
                Logger.getLogger(HiloClienteUsuario.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } while (true);
        
        
    }

    private void modificarPreferencias(Usuario u) throws SQLException {
        this.con.abrirConexion();
        this.con.modPrefs(u);
        this.con.cerrarConexion();
    }
    
    private Usuario obtenerUsuario(String idUser) throws SQLException {
        this.con.abrirConexion();
        Usuario u = this.con.obtenerUsuario(idUser);
        this.con.cerrarConexion();
        return u;
    }
    
}
