
package finalpsp_servidor;

import controlador.Conexion;
import java.net.Socket;
import java.security.PrivateKey;
import java.security.PublicKey;

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
        
        
        
    }
    
}
