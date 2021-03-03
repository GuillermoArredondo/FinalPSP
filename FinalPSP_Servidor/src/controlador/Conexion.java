
package controlador;
import Datos.Usuario;
import Utilities.Seguridad;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JOptionPane;

/**
 *
 * @author Guille
 */
public class Conexion {
    
    //********************* Atributos *************************
    private java.sql.Connection Conex;
    //Atributo a través del cual hacemos la conexión física.
    private java.sql.Statement Sentencia_SQL;
    //Atributo que nos permite ejecutar una sentencia SQL
    private java.sql.ResultSet Conj_Registros;
    //(Cursor) En él están almacenados los datos.
    
    public Conexion() {    
    }
    
    public void abrirConexion() {
        try {
            System.out.println("Estoy en abrir conexion");
            Class.forName("com.mysql.jdbc.Driver");
            //Realizamos la conexión a una BD con un usuario y una clave.
            Conex = java.sql.DriverManager.getConnection("jdbc:mysql://localhost/psp","root","");
            Sentencia_SQL = Conex.createStatement();
            System.out.println("Conexion realizada con éxito");
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
    
    public void cerrarConexion() {
        try {
            // resultado.close();
            this.Conex.close();
            System.out.println("Desconectado de la Base de Datos"); // Opcional para seguridad
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error de Desconexion", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    public ArrayList<String> obtenerUsuarios() throws SQLException{
        
        ArrayList<String> lista = new ArrayList<>();
        String sentencia = "SELECT email FROM usuarios";
        Conj_Registros = Sentencia_SQL.executeQuery(sentencia);
        
        while(Conj_Registros.next()){
            lista.add(Conj_Registros.getString(1));
        }
        return lista;
    }
    
    public void registrarUsuario(Usuario u){
        
        try{
            String sentencia = "INSERT INTO usuarios (id, email, pwd, nick, edad, foto) "
                + "values('"+u.getId()+"','"+u.getEmail()+"','"+Seguridad.Hexadecimal(u.getPwd())+
                 "','"+u.getNick()+"',"+u.getEdad()+" ,null)";
        Sentencia_SQL.executeUpdate(sentencia);
        System.out.println("USUARIO REGISTRADO 1 OK");
        
        sentencia = "INSERT INTO preferencias (id, interes, relacion, t_hijos, q_hijos, deporte, arte, politica)"
                + "values('"+u.getId()+"', "+u.getInteres()+", "+u.getRelacion()+", '"+u.getT_hijos()+"',"
                + " '"+u.getQ_hijos()+"', "+u.getDeporte()+", "+u.getArte()+", "+u.getPolitica()+")";
        Sentencia_SQL.executeUpdate(sentencia);
        System.out.println("USUARIO REGISTRADO 2 OK");
        }catch(Exception e){
            System.out.println(e);
        }
        
        
    }
    
    public String obtenerPass(String name) throws SQLException{
        
        String res = "";
        String sentencia = "SELECT password FROM user where nameuser='"+name+"'";
        Conj_Registros = Sentencia_SQL.executeQuery(sentencia);
        
        while(Conj_Registros.next()){
            res = Conj_Registros.getString(1);
        }
        
        return res;
    }
    
}
