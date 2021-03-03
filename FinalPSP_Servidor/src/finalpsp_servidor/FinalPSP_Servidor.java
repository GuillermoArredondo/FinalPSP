/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalpsp_servidor;

import Utilities.Seguridad;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 *
 * @author Guille
 */
public class FinalPSP_Servidor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        
        ServerSocket servidor;
        servidor = new ServerSocket(34000);
        KeyPair par = Seguridad.generarClaves();
        PrivateKey clavePriv = par.getPrivate();
        PublicKey clavePub = par.getPublic();
        
        
        while(true){
            Socket cliente = servidor.accept();
            HiloClientes hc = new HiloClientes(cliente, clavePub, clavePriv);
            hc.start();
        }
        
    }
    
}
