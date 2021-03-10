
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
class HiloClienteUsuario extends Thread{
    
    private String idUser;
    private Socket cliente;
    private PublicKey clavePubAjena;
    private PrivateKey clavePrivPropia;
    private Conexion con;
    
    public HiloClienteUsuario(String idUser, Socket cliente, PrivateKey clavePrivPropia, PublicKey clavePubAjena, Conexion con) {
        this.idUser = idUser;
        this.cliente = cliente;
        this.clavePrivPropia = clavePrivPropia;
        this.clavePubAjena = clavePubAjena;
        this.con = con;
    }
    
    
    @Override
    public void run(){
        
        try {
            //enviar lista de amigos
            enviarListaAmigos();
            
            //enviar lista de usuarios
            enviarListaUsuarios();
            
            boolean activo = true;
            do {
                
                try {
                    
                    //recibo orden del cliente
                    SealedObject so = (SealedObject) Comunicacion.recibirObjeto(cliente);
                    int orden = (int) Seguridad.descifrar(clavePrivPropia, so);
                    
                    //orden 0 -> Modificar preferencias del usuario
                    //      1 -> Marcar me gusta
                    //      2 -> Mostrar usuarios afines
                    //      3 -> Mostrar todos los usuarios
                    
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
                            so = (SealedObject) Comunicacion.recibirObjeto(cliente);
                            ArrayList<String> idsMegusta = (ArrayList<String>) Seguridad.descifrar(clavePrivPropia, so);
                            checkMatch(idsMegusta);
                            enviarListaAmigos();
                            
                            break;
                            
                        case 2:
                            //recibo el usuario con el que voy a filtrar
                            so = (SealedObject) Comunicacion.recibirObjeto(cliente);
                            u = (Usuario) Seguridad.descifrar(clavePrivPropia, so);
                            ArrayList<Usuario> listaFiltrada = crearListaFiltrada(u);
                            Comunicacion.enviarObjeto(cliente, listaFiltrada);
                            
                            
                            break;
                            
                        case 3:
                            enviarListaUsuarios();
                            
                            break;
                            
                        case 4:
                            //recibo los ids de los Users
                            so = (SealedObject) Comunicacion.recibirObjeto(cliente);
                            ArrayList<String> idsUsers = (ArrayList<String>) Seguridad.descifrar(clavePrivPropia, so);
                            HiloChat hc = new HiloChat(idsUsers, cliente, clavePrivPropia, clavePubAjena, con);
                            hc.start();
                            activo = false;
                            break;
                            
                    }
                    
                    
                    
                } catch (IOException | ClassNotFoundException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | SQLException ex) {
                    Logger.getLogger(HiloClienteUsuario.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            } while (activo);
        } catch (SQLException | IOException  ex) {
            Logger.getLogger(HiloClienteUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    private void enviarListaAmigos() throws SQLException{
        try {
            ArrayList<String> listaAmigos = obtenerListaAmigos(this.idUser);
            SealedObject so = Seguridad.cifrar(clavePubAjena, listaAmigos);
            Comunicacion.enviarObjeto(cliente, so);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | IllegalBlockSizeException ex) {
            Logger.getLogger(HiloClienteUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void enviarListaUsuarios() throws SQLException, IOException{
        ArrayList<Usuario> listaUsuarios = obtenerListaUsuarios();
        //so = Seguridad.cifrar(clavePubAjena, listaUsuarios);
        Comunicacion.enviarObjeto(cliente, listaUsuarios);
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

    private ArrayList<String> obtenerListaAmigos(String idUser) throws SQLException {
        this.con.abrirConexion();
        ArrayList<String> listaIdsAmigos = this.con.obtenerListaAmigos(idUser);
        ArrayList<String> listaNicksAmigos = this.con.obtenerListaNicksAmigos(listaIdsAmigos);
        this.con.cerrarConexion();
        return listaNicksAmigos;
    }

        private ArrayList<Usuario> obtenerListaUsuarios() throws SQLException {
        this.con.abrirConexion();
        ArrayList<Usuario> listaUsuarios = this.con.obtenerAllUsers();
        this.con.cerrarConexion();
        return listaUsuarios;
    }

    private void checkMatch(ArrayList<String> idsMegusta) throws SQLException {
        boolean match = false;
        this.con.abrirConexion();
        if (this.con.checkMatch(idsMegusta)) {
            this.con.doMatch(idsMegusta);
            System.out.println("No hay match");
        }else{
            this.con.crearMatch(idsMegusta);
        }
        this.con.cerrarConexion();
    }

    private ArrayList<Usuario> crearListaFiltrada(Usuario u) throws SQLException {
        this.con.abrirConexion();
        ArrayList<Usuario> listaFiltrada = this.con.crearListaFiltrada(u);
        this.con.cerrarConexion();
        return listaFiltrada;
    }
    
}
