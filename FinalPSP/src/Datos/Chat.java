
package Datos;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Guille
 */
public class Chat implements Serializable{
    
    private ArrayList<String> listaNicks;
    private ArrayList<String> msgs;

    public Chat(ArrayList<String> listaNicks, ArrayList<String> msgs) {
        this.listaNicks = listaNicks;
        this.msgs = msgs;
    }

    public ArrayList<String> getListaNicks() {
        return listaNicks;
    }

    public void setListaNicks(ArrayList<String> listaNicks) {
        this.listaNicks = listaNicks;
    }

    public ArrayList<String> getMsgs() {
        return msgs;
    }

    public void setMsgs(ArrayList<String> msgs) {
        this.msgs = msgs;
    }
    
    
    
}
