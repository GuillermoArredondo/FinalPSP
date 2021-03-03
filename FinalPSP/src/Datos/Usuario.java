
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
    private byte[] pwd;
    private String nick;
    private int edad;
    private byte[] foto;
    private int interes;
    private int relacion;
    private char t_hijos;
    private char q_hijos;
    private int deporte;
    private int arte;
    private int politica;
    
    public Usuario(String email, byte[] pwd, String nick, int edad, byte[] foto,
                   int interes, int relacion, char t_hijos,
                   char q_hijos, int deporte, int arte, int politica){
        
        this.id = generateId();
        this.email = email;
        this.pwd = pwd;
        this.nick = nick;
        this.edad = edad;
        this.foto = foto;
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
    
    public static String generateId() {
        String uuid = UUID.randomUUID().toString();
        return uuid;
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

    public byte[] getPwd() {
        return pwd;
    }

    public void setPwd(byte[] pwd) {
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