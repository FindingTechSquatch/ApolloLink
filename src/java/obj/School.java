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
public class School implements Serializable{
    
    private int SID;
    private String schlName;
    private String schlAddr1;
    private String schlAddr2;
    private String schlCity;
    private String schlST;
    private String schlZip;
    private String schlSize;
    private String schlPhone;
    private String logoURL;
    private ArrayList<Group> grps;

    public School() {
    }

    public School(int SID, String schlName, String schlAddr1, String schlAddr2, String schlCity, String schlST, String schlZip, String schlSize, String schlPhone, String logoURL, ArrayList<Group> grps) {
        this.SID = SID;
        this.schlName = schlName;
        this.schlAddr1 = schlAddr1;
        this.schlAddr2 = schlAddr2;
        this.schlCity = schlCity;
        this.schlST = schlST;
        this.schlZip = schlZip;
        this.schlSize = schlSize;
        this.schlPhone = schlPhone;
        this.logoURL = logoURL;
        this.grps = grps;
    }

    
    
    public int getSID() {
        return SID;
    }

    public void setSID(int SID) {
        this.SID = SID;
    }

    public String getSchlName() {
        return schlName;
    }

    public void setSchlName(String schlName) {
        this.schlName = schlName;
    }

    public String getSchlAddr1() {
        return schlAddr1;
    }

    public void setSchlAddr1(String schlAddr1) {
        this.schlAddr1 = schlAddr1;
    }

    public String getSchlAddr2() {
        return schlAddr2;
    }

    public void setSchlAddr2(String schlAddr2) {
        this.schlAddr2 = schlAddr2;
    }

    public String getSchlCity() {
        return schlCity;
    }

    public void setSchlCity(String schlCity) {
        this.schlCity = schlCity;
    }

    public String getSchlST() {
        return schlST;
    }

    public void setSchlST(String schlST) {
        this.schlST = schlST;
    }

    public String getSchlZip() {
        return schlZip;
    }

    public void setSchlZip(String schlZip) {
        this.schlZip = schlZip;
    }

    public String getSchlSize() {
        return schlSize;
    }

    public void setSchlSize(String schlSize) {
        this.schlSize = schlSize;
    }

    public String getSchlPhone() {
        return schlPhone;
    }

    public void setSchlPhone(String schlPhone) {
        this.schlPhone = schlPhone;
    }

    public String getLogoURL() {
        return logoURL;
    }

    public void setLogoURL(String logoURL) {
        this.logoURL = logoURL;
    }

    public ArrayList<Group> getGrps() {
        return grps;
    }

    public void setGrps(ArrayList<Group> grps) {
        this.grps = grps;
    }
    
    
    
}
