
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;

/**
 *
 * @author Guille
 */
class HiloClienteAdmin extends Thread{
    
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
    public void run(){
        
        try {
            System.out.println("HILO CLIENTE ADMIN");
            //enviar la lista de usuarios y la lista de admins
            ArrayList<Usuario> listaAdmin = obtenerAdmins();
            SealedObject so = Seguridad.cifrar(clavePubAjena, listaAdmin);
            Comunicacion.enviarObjeto(cliente, so);
            
            ArrayList<Usuario> listaUsuarios = obtenerUsuarios();
            so = Seguridad.cifrar(clavePubAjena, listaUsuarios);
            Comunicacion.enviarObjeto(cliente, so); 
            
            
            
            
            
        } catch (SQLException ex) {
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | IllegalBlockSizeException ex) {
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
    
}
