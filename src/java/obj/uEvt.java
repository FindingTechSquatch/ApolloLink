/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obj;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author tyleryork
 */
public class uEvt extends uDetail implements Serializable{
    
    private ArrayList<Event> evts;

    public uEvt() {
    }

    public uEvt(ArrayList<Event> evts, int uid, String fName, String lName, String phone, String uus, String hus, String hpw) {
        super(uid, fName, lName, phone, uus, hus, hpw);
        this.evts = evts;
    }

    
    
    public ArrayList<Event> getEvts() {
        return evts;
    }

    public void setEvts(ArrayList<Event> evts) {
        this.evts = evts;
    }
    
    
    
}
