/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalpsp;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import vista.VentanaLogin;

/**
 *
 * @author Guille
 */
public class FinalPSP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, ClassNotFoundException {
        
        VentanaLogin vl = new VentanaLogin();
        vl.setVisible(true);
        vl.setLocationRelativeTo(null);
        
    }
    
}
