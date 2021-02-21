
package controlador;
import Datos.Usuario;
import java.sql.SQLException;
import java.util.ArrayList;
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
            
            Class.forName("com.mysql.jdbc.Driver");
            //Realizamos la conexión a una BD con un usuario y una clave.
            Conex = java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306/psp","root","");
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
        String sentencia = "SELECT nameuser FROM user";
        Conj_Registros = Sentencia_SQL.executeQuery(sentencia);
        
        while(Conj_Registros.next()){
            lista.add(Conj_Registros.getString(1));
        }
        return lista;
    }
    
    public void registrarUsuario(Usuario u) throws SQLException{
        
        String sentencia = "INSERT INTO user (nameuser, password, email)  values('"+u.getUsuario()+"','"+u.getPass()+"','"+u.getEmail()+"')";
        Sentencia_SQL.executeUpdate(sentencia);
        
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
