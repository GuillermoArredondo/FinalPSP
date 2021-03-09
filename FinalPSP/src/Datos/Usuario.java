
package Datos;

import java.io.Serializable;
import java.util.UUID;

/**
 *
 * @author Guille
 */
public class Usuario implements Serializable{
    
    private String id;
    private String email;
    private String pwd;
    private String nick;
    private int edad;
    private byte[] foto;
    private int genero;
    private int interes;
    private int relacion;
    private char t_hijos;
    private char q_hijos;
    private int deporte;
    private int arte;
    private int politica;
    private int activo;
    
    public Usuario(String email, String pwd, String nick, int edad, byte[] foto,
                   int genero, int interes, int relacion, char t_hijos,
                   char q_hijos, int deporte, int arte, int politica, int activo){
        
        this.id = generateId();
        this.email = email;
        this.pwd = pwd;
        this.nick = nick;
        this.edad = edad;
        this.foto = foto;
        this.genero = genero;
        this.interes = interes;
        this.relacion = relacion;
        this.t_hijos = t_hijos;
        this.q_hijos = q_hijos;
        this.deporte = deporte;
        this.arte = arte;
        this.politica = politica;
    }

    public Usuario() {
    }
    
    public Usuario(String id, String email, String pwd, String nick, byte[] foto) {
        this.id = id;
        this.email = email;
        this.pwd = pwd;
        this.nick = nick;
        this.foto = foto;
    }

    public Usuario(String id, String email, String nick) {
        this.id = id;
        this.email = email;
        this.nick = nick;
    }

    public Usuario(String id, String email, String nick, int edad, int activo) {
        this.id = id;
        this.email = email;
        this.nick = nick;
        this.edad = edad;
        this.activo = activo;
    }
    
    public Usuario(String email, String pwd) {
        this.email = email;
        this.pwd = pwd;
    }

    public static String generateId() {
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }

    public int getGenero() {
        return genero;
    }

    public void setGenero(int genero) {
        this.genero = genero;
    }
    
    

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }
    
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public int getInteres() {
        return interes;
    }

    public void setInteres(int interes) {
        this.interes = interes;
    }

    public int getRelacion() {
        return relacion;
    }

    public void setRelacion(int relacion) {
        this.relacion = relacion;
    }

    public char getT_hijos() {
        return t_hijos;
    }

    public void setT_hijos(char t_hijos) {
        this.t_hijos = t_hijos;
    }

    public char getQ_hijos() {
        return q_hijos;
    }

    public void setQ_hijos(char q_hijos) {
        this.q_hijos = q_hijos;
    }

    public int getDeporte() {
        return deporte;
    }

    public void setDeporte(int deporte) {
        this.deporte = deporte;
    }

    public int getArte() {
        return arte;
    }

    public void setArte(int arte) {
        this.arte = arte;
    }

    public int getPolitica() {
        return politica;
    }

    public void setPolitica(int politica) {
        this.politica = politica;
    }
    
    
}