/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author Guille
 */
public class Comunicacion {
    
    public static void enviarObjeto(Socket cliente, Object o) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
        oos.writeObject(o);
    }

    public static Object recibirObjeto(Socket cliente) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream());

        return ois.readObject();
    }

    public static void enviarString(Socket cliente, String s) throws IOException {
        DataOutputStream dos = new DataOutputStream(cliente.getOutputStream());
        dos.writeUTF(s);
    }

    public static String recibirString(Socket cliente) throws IOException {
        DataInputStream dis = new DataInputStream(cliente.getInputStream());

        return dis.readUTF();
    }
}
