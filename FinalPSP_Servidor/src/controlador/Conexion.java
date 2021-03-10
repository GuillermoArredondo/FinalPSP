package controlador;

import Datos.Chat;
import Datos.Usuario;
import Utilities.Seguridad;
import com.sun.javafx.scene.control.skin.FXVK;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
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
            //System.out.println("Estoy en abrir conexion");
            Class.forName("com.mysql.jdbc.Driver");
            //Realizamos la conexión a una BD con un usuario y una clave.
            Conex = java.sql.DriverManager.getConnection("jdbc:mysql://localhost/psp", "root", "");
            Sentencia_SQL = Conex.createStatement();
            //System.out.println("Conexion realizada con éxito");
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

    public ArrayList<String> obtenerUsuarios() throws SQLException {

        ArrayList<String> lista = new ArrayList<>();
        String sentencia = "SELECT email FROM usuarios";
        Conj_Registros = Sentencia_SQL.executeQuery(sentencia);

        while (Conj_Registros.next()) {
            lista.add(Conj_Registros.getString(1));
        }
        return lista;
    }

    public Usuario obtenerUsuario(String id) throws SQLException {
        Usuario u = new Usuario();
        String sentencia = "SELECT * FROM usuarios WHERE id = '" + id + "'";
        Conj_Registros = Sentencia_SQL.executeQuery(sentencia);

        while (Conj_Registros.next()) {
            u.setId(Conj_Registros.getString(1));
            u.setEmail(Conj_Registros.getString(2));
            u.setPwd(Conj_Registros.getString(3));
            u.setNick(Conj_Registros.getString(4));
            u.setEdad(Conj_Registros.getInt(5));
            u.setFoto(null);
            u.setActivo(Conj_Registros.getInt(7));
        }

        sentencia = "SELECT * FROM preferencias WHERE id = '" + id + "'";
        Conj_Registros = Sentencia_SQL.executeQuery(sentencia);

        while (Conj_Registros.next()) {
            u.setGenero(Conj_Registros.getInt(2));
            u.setInteres(Conj_Registros.getInt(3));
            u.setRelacion(Conj_Registros.getInt(4));
            u.setT_hijos(Conj_Registros.getString(5).charAt(0));
            u.setQ_hijos(Conj_Registros.getString(6).charAt(0));
            u.setDeporte(Conj_Registros.getInt(7));
            u.setArte(Conj_Registros.getInt(8));
            u.setPolitica(Conj_Registros.getInt(9));
        }
        return u;
    }

    public void registrarUsuario(Usuario u) {

        try {
            String sentencia = "INSERT INTO usuarios (id, email, pwd, nick, edad, foto, activo) "
                    + "values('" + u.getId() + "','" + u.getEmail() + "','" + u.getPwd()
                    + "','" + u.getNick() + "'," + u.getEdad() + " ,null, 0)";
            Sentencia_SQL.executeUpdate(sentencia);
            System.out.println("USUARIO REGISTRADO 1 OK");

            sentencia = "INSERT INTO preferencias (id, genero, interes, relacion, t_hijos, q_hijos, deporte, arte, politica)"
                    + "values('" + u.getId() + "',"+u.getGenero()+" , " + u.getInteres() + ", " + u.getRelacion() + ", '" + u.getT_hijos() + "',"
                    + " '" + u.getQ_hijos() + "', " + u.getDeporte() + ", " + u.getArte() + ", " + u.getPolitica() + ")";
            Sentencia_SQL.executeUpdate(sentencia);
            System.out.println("USUARIO REGISTRADO 2 OK");

            sentencia = "INSERT INTO roles_usuarios VALUES('" + u.getId() + "', 1)";
            Sentencia_SQL.executeUpdate(sentencia);
            System.out.println("USUARIO REGISTRADO 3 OK");

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public String obtenerPass(String email) throws SQLException {

        String res = "";
        String sentencia = "SELECT pwd FROM usuarios where email='" + email + "'";
        Conj_Registros = Sentencia_SQL.executeQuery(sentencia);

        while (Conj_Registros.next()) {
            res = Conj_Registros.getString(1);
        }

        return res;
    }

    public ArrayList<String> obtenerUsuariosAdmins() throws SQLException {
        ArrayList<String> lista = new ArrayList<>();
        String sentencia = "SELECT id FROM roles_usuarios WHERE rol = 0";
        Conj_Registros = Sentencia_SQL.executeQuery(sentencia);

        while (Conj_Registros.next()) {
            lista.add(Conj_Registros.getString(1));
        }
        return lista;
    }

    public String obtenerIdUser(String email) throws SQLException {

        String idUser = "";
        String sentencia = "SELECT id FROM usuarios where email='" + email + "'";
        Conj_Registros = Sentencia_SQL.executeQuery(sentencia);

        while (Conj_Registros.next()) {
            idUser = Conj_Registros.getString(1);
        }

        return idUser;
    }

    public Usuario obtenerUsuAdmin(String idUser) throws SQLException {

        String sentencia = "SELECT email, pwd, nick, foto FROM usuarios where id='" + idUser + "'";
        Conj_Registros = Sentencia_SQL.executeQuery(sentencia);
        Usuario usuA = new Usuario();
        while (Conj_Registros.next()) {
            usuA = new Usuario(idUser,
                    Conj_Registros.getString(1),
                    Conj_Registros.getString(2),
                    Conj_Registros.getString(3),
                    null);
        }
        return usuA;
    }

    public ArrayList<Usuario> obtenerAdmins() throws SQLException {
        ArrayList<String> listaIdsAdmin = new ArrayList<>();
        String sentencia = "SELECT id FROM roles_usuarios where rol=0";
        Conj_Registros = Sentencia_SQL.executeQuery(sentencia);
        while (Conj_Registros.next()) {
            listaIdsAdmin.add(Conj_Registros.getString(1));
        }
        System.out.println("Lista de IDs admins: " + listaIdsAdmin.size());
        ArrayList<Usuario> listaAdmins = new ArrayList<>();
        for (int i = 0; i < listaIdsAdmin.size(); i++) {
            sentencia = "SELECT id, email, nick FROM usuarios where id='" + listaIdsAdmin.get(i) + "'";
            Conj_Registros = Sentencia_SQL.executeQuery(sentencia);
            while (Conj_Registros.next()) {
                String id = Conj_Registros.getString(1);
                String email = Conj_Registros.getString(2);
                String nick = Conj_Registros.getString(3);
                Usuario u = new Usuario(id, email, nick);
                listaAdmins.add(u);
            }
        }
        System.out.println("Lista de admins: " + listaAdmins.size());
        return listaAdmins;
    }

    public ArrayList<Usuario> ObtenerUsus() throws SQLException {
        ArrayList<String> listaIdsUsuarios = new ArrayList<>();
        String sentencia = "SELECT id FROM roles_usuarios where rol = 1";
        Conj_Registros = Sentencia_SQL.executeQuery(sentencia);
        while (Conj_Registros.next()) {
            listaIdsUsuarios.add(Conj_Registros.getString(1));
        }
        System.out.println("Lista de IDs usuarios: " + listaIdsUsuarios.size());
        ArrayList<Usuario> listaUsuarios = new ArrayList<>();
        for (int i = 0; i < listaIdsUsuarios.size(); i++) {
            sentencia = "SELECT id, email, nick, edad, activo FROM usuarios where id='" + listaIdsUsuarios.get(i) + "'";
            Conj_Registros = Sentencia_SQL.executeQuery(sentencia);
            while (Conj_Registros.next()) {
                String id = Conj_Registros.getString(1);
                String email = Conj_Registros.getString(2);
                String nick = Conj_Registros.getString(3);
                int edad = Conj_Registros.getInt(4);
                int activo = Conj_Registros.getInt(5);

                Usuario u = new Usuario(id, email, nick, edad, activo);
                listaUsuarios.add(u);
            }
        }
        System.out.println("Lista de usuarios: " + listaUsuarios.size());
        return listaUsuarios;

    }

    public void activarUser(String idUser) throws SQLException {
        String sentencia = "update usuarios set activo = 1 where id = '" + idUser + "'";
        Sentencia_SQL.executeUpdate(sentencia);
    }

    public void convertirAdminUser(String idUser) throws SQLException {
        String sentencia = "INSERT INTO roles_usuarios VALUES('" + idUser + "', 0)";
        Sentencia_SQL.executeUpdate(sentencia);
    }

    public void eliminarUser(String idUser) throws SQLException {
        String sentencia = "delete from preferencias where id = '" + idUser + "'";
        Sentencia_SQL.executeUpdate(sentencia);
        System.out.println("DELETE 1 OK");

        sentencia = "delete from roles_usuarios where id = '" + idUser + "'";
        Sentencia_SQL.executeUpdate(sentencia);
        System.out.println("DELETE 2 OK");

        sentencia = "delete from usuarios where id = '" + idUser + "'";
        Sentencia_SQL.executeUpdate(sentencia);
        System.out.println("DELETE 3 OK");

    }

    public void registrarUsuarioByAdmin(Usuario u) {
        try {
            String sentencia = "INSERT INTO usuarios (id, email, pwd, nick, edad, activo) "
                    + "values('" + u.getId() + "','" + u.getEmail() + "','" + u.getPwd()
                    + "','" + u.getNick() + "'," + u.getEdad() + " , "+u.getActivo()+")";
            Sentencia_SQL.executeUpdate(sentencia);
            System.out.println("USUARIO REGISTRADO 1 OK");

            sentencia = "INSERT INTO roles_usuarios VALUES('" + u.getId() + "', 1)";
            Sentencia_SQL.executeUpdate(sentencia);
            System.out.println("USUARIO REGISTRADO 2 OK");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void quitarAdmin(String idUser) throws SQLException {
        String sentencia = "delete from roles_usuarios where id = '" + idUser + "' and rol = 0";
        Sentencia_SQL.executeUpdate(sentencia);
    }

    public void registrarAdmin(Usuario u) {
        try {
            String sentencia = "INSERT INTO usuarios (id, email, pwd, nick, edad, activo) "
                    + "values('" + u.getId() + "','" + u.getEmail() + "','" + u.getPwd()
                    + "','" + u.getNick() + "'," + u.getEdad() + " , "+u.getActivo()+")";
            Sentencia_SQL.executeUpdate(sentencia);
            System.out.println("ADMIN REGISTRADO 1 OK");

            sentencia = "INSERT INTO roles_usuarios VALUES('" + u.getId() + "', 0)";
            Sentencia_SQL.executeUpdate(sentencia);
            System.out.println("ADMIN REGISTRADO 2 OK");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void modPrefs(Usuario u) throws SQLException {
        String sentencia = "update preferencias set interes = "+u.getInteres()+","
                + " relacion = "+u.getRelacion()+", t_hijos = '"+u.getT_hijos()+"',"
                + " q_hijos = '"+u.getQ_hijos()+"', deporte = "+u.getDeporte()+","
                + " arte = "+u.getArte()+", politica = "+u.getPolitica()+""
                + "  WHERE id = '" + u.getId() + "'";
        Sentencia_SQL.executeUpdate(sentencia);
    }

    public ArrayList<String> obtenerListaAmigos(String idUser) throws SQLException {
        ArrayList<String> listaIds = new ArrayList<>();
        ArrayList<String> listaAmigos = new ArrayList<>();
        String sentencia = "SELECT id1, id2 FROM likes where (id1 = '"+idUser+"' or id2 = '"+idUser+"') and like_rec = 1" ;
        Conj_Registros = Sentencia_SQL.executeQuery(sentencia);
        while (Conj_Registros.next()) {
            listaIds.add(Conj_Registros.getString(1));
            listaIds.add(Conj_Registros.getString(2));
        }
        for (int i = 0; i < listaIds.size(); i++) {
            if (!listaIds.get(i).equals(idUser)) {
                listaAmigos.add(listaIds.get(i));
            }
        }
        return listaAmigos;
    }

    public ArrayList<String> obtenerListaNicksAmigos(ArrayList<String> listaIdsAmigos) throws SQLException {
        ArrayList<String> listaAmigos = new ArrayList<>();
        for (int i = 0; i < listaIdsAmigos.size(); i++) {
            String sentencia = "select nick from usuarios where id = '"+listaIdsAmigos.get(i)+"'";
            Conj_Registros = Sentencia_SQL.executeQuery(sentencia);
            while (Conj_Registros.next()) {
                listaAmigos.add(Conj_Registros.getString(1));
            }
        }
        return listaAmigos;
        
    }

    public ArrayList<Usuario> obtenerAllUsers() throws SQLException {
        ArrayList<String> listaIdsUsers = new ArrayList<>();
        String sentencia = "select id from usuarios";
            Conj_Registros = Sentencia_SQL.executeQuery(sentencia);
            while (Conj_Registros.next()) {
                listaIdsUsers.add(Conj_Registros.getString(1));
            }
        ArrayList<Usuario> listaUsuarios = new ArrayList<>();
        for (int i = 0; i < listaIdsUsers.size(); i++) {
            listaUsuarios.add(obtenerUsuario(listaIdsUsers.get(i)));
        }
        return listaUsuarios;
    }

    public boolean checkMatch(ArrayList<String> idsMegusta) throws SQLException {
        String sentencia = "select like_rec from likes where (id1 = '"+idsMegusta.get(1)+"' and id2 = '"+idsMegusta.get(0)+"') or (id1 = '"+idsMegusta.get(0)+"' and id2 = '"+idsMegusta.get(1)+"')";
        Conj_Registros = Sentencia_SQL.executeQuery(sentencia);
        boolean ok = false;
        while (Conj_Registros.next()) {
                ok = true;
        }
        return ok;
    }

    public void doMatch(ArrayList<String> idsMegusta) throws SQLException {
        String sentencia = "update likes set like_rec = 1 where (id1 = '"+idsMegusta.get(1)+"' and id2 = '"+idsMegusta.get(0)+"') or (id1 = '"+idsMegusta.get(0)+"' and id2 = '"+idsMegusta.get(1)+"')";
        Sentencia_SQL.executeUpdate(sentencia);
        System.out.println("doMatch");
    }

    public void crearMatch(ArrayList<String> idsMegusta) throws SQLException {
        String id = UUID.randomUUID().toString();
        String sentencia = "insert into likes values ('"+id+"', '"+idsMegusta.get(0)+"', '"+idsMegusta.get(1)+"', 0)";
        Sentencia_SQL.executeUpdate(sentencia);
        System.out.println("crearMatch");
    }

    public ArrayList<Usuario> crearListaFiltrada(Usuario u) throws SQLException {
        
        ArrayList<String> listaGen = obtenerPorGenero(u.getInteres());
        ArrayList<String> listaFilt = new ArrayList<String>();
        
        for (int i = 0; i < listaGen.size(); i++) {
            String sentencia = "select id from preferencias where id = '"+listaGen.get(i)+"'"
                    + " and relacion = "+u.getRelacion()+""
                    + " and t_hijos = '"+u.getT_hijos()+"'"
                    + " and q_hijos ='"+u.getQ_hijos()+"'"
                    + " and (deporte between "+(u.getDeporte()-30)+" and "+(u.getDeporte()+30)+")"
                    + " and (deporte between "+(u.getArte()-30)+" and "+(u.getArte()+30)+")"
                    + " and (deporte between "+(u.getPolitica()-30)+" and "+(u.getPolitica()+30)+")";
            Conj_Registros = Sentencia_SQL.executeQuery(sentencia);
            while (Conj_Registros.next()) {
                listaFilt.add(Conj_Registros.getString(1));
            }
        }
        
        ArrayList<Usuario> listaUsus = new ArrayList<Usuario>();
        
        for (int i = 0; i < listaFilt.size(); i++) {
            listaUsus.add(obtenerUsuario(listaFilt.get(i)));
        }
        
        return listaUsus;
    }
    
    private ArrayList<String> obtenerPorGenero(int gen) throws SQLException{
        ArrayList<String> listaPorGenero = new ArrayList<>();
        String sentencia = "select id from preferencias where genero = "+gen+"";
            Conj_Registros = Sentencia_SQL.executeQuery(sentencia);
            while (Conj_Registros.next()) {
                listaPorGenero.add(Conj_Registros.getString(1));
            }
        return listaPorGenero;  
    }

    public boolean existeChat(ArrayList<String> idsUsers) {
        boolean existe = false;
        try{
            String sentencia = "select id from "+idsUsers.get(0)+"_"+idsUsers.get(1)+"";
            Conj_Registros = Sentencia_SQL.executeQuery(sentencia);
            while (Conj_Registros.next()) {
                existe = true;
                System.out.println("EXISTE CHAT");
            }
            
        }catch(Exception e){
            
        }
        return existe;
        
    }

    public void crearChat(ArrayList<String> idsUsers) throws SQLException {
        String sentencia = "create table "+idsUsers.get(0)+"_"+idsUsers.get(1)+" (id int AUTO_INCREMENT, id_u varchar(50), msg varchar(200), PRIMARY KEY (id))";
        Sentencia_SQL.executeUpdate(sentencia);
        sentencia = "insert into "+idsUsers.get(0)+"_"+idsUsers.get(1)+"(id, id_u, msg) values (1, '"+idsUsers.get(0)+"', '')";
        Sentencia_SQL.executeUpdate(sentencia);
        System.out.println("CREO CHAT");
    }

    public Chat ObtenerChat(ArrayList<String> idsUsers) throws SQLException {
        
        ArrayList<String> listaNicks = new ArrayList<>();
        ArrayList<String> msgs = new ArrayList<>();
        
        listaNicks = obtenerListaNicksAmigos(idsUsers);
        int numMsg = 0;
        
        String sentencia = "select count(msg) from "+idsUsers.get(0)+"_"+idsUsers.get(1)+"";
        Conj_Registros = Sentencia_SQL.executeQuery(sentencia);
            while (Conj_Registros.next()) {
                numMsg = Conj_Registros.getInt(1);
            }
        ArrayList<String> listaNicksDefs = new ArrayList<>();
        
            sentencia = "select id_u, msg from "+idsUsers.get(0)+"_"+idsUsers.get(1)+"";
            Conj_Registros = Sentencia_SQL.executeQuery(sentencia);
            while (Conj_Registros.next()) {
                String id_u = Conj_Registros.getString(1);
                if (id_u.equals(idsUsers.get(0))) {
                    id_u = listaNicks.get(0);
                }else{
                    id_u = listaNicks.get(1);
                }
                String msg = Conj_Registros.getString(2);
                listaNicksDefs.add(id_u);
                msgs.add(msg);
            }
        
        
        System.out.println("OBTENGO CHAT");
        
        
        return new Chat(listaNicksDefs, msgs);
    }

    public void enviarMensage(ArrayList<String> idsUsers, String msg, String miId) throws SQLException {
        
        String sentencia = "insert into "+idsUsers.get(0)+"_"+idsUsers.get(1)+" (id_u, msg) values ('"+miId+"', '"+msg+"' )";
        Sentencia_SQL.executeUpdate(sentencia);
        
    }

}
