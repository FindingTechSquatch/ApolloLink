/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obj;

import java.io.Serializable;
import java.util.ArrayList;
import obj.*;

/**
 *
 * @author tyleryork
 */
public class uDir extends uDetail implements Serializable{
    
    private ArrayList<School> schls;

    public uDir() {
    }

    public uDir(ArrayList<School> schls, int uid, String fName, String lName, String phone, String uus, String hus, String hpw) {
        super(uid, fName, lName, phone, uus, hus, hpw);
        this.schls = schls;
    }

    
    
    public ArrayList<School> getSchls() {
        return schls;
    }

    public void setSchls(ArrayList<School> schls) {
        this.schls = schls;
    }
    
    public uBase touBase() {
        uBase u = new uBase();
        u.setHpw(this.getHpw());
        u.setHus(this.getHus());
        u.setUus(this.getUus());
        return u;
    }
    
}
