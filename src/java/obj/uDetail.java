/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obj;

import java.io.Serializable;

/**
 *
 * @author tyleryork
 */
public class uDetail extends uBase implements Serializable {
    
    private int uid;
    private String fName;
    private String lName;
    private String phone;

    public uDetail() {
    }

    public uDetail(int uid, String fName, String lName, String phone, String uus, String hus, String hpw) {
        super(uus, hus, hpw);
        this.uid = uid;
        this.fName = fName;
        this.lName = lName;
        this.phone = phone;
    }
    
    

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
