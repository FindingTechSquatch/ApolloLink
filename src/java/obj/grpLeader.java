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
public class grpLeader implements Serializable{
    
    private int LID;
    private String ldrFName;
    private String ldrLName;
    private String ldrSchlYr;
    private String ldrTitle;

    public grpLeader() {
    }

    public grpLeader(int LID, String ldrFName, String ldrLName, String ldrSchlYr, String ldrTitle) {
        this.LID = LID;
        this.ldrFName = ldrFName;
        this.ldrLName = ldrLName;
        this.ldrSchlYr = ldrSchlYr;
        this.ldrTitle = ldrTitle;
    }
    
    public int getLID() {
        return LID;
    }

    public void setLID(int LID) {
        this.LID = LID;
    }

    public String getLdrFName() {
        return ldrFName;
    }

    public void setLdrFName(String ldrFName) {
        this.ldrFName = ldrFName;
    }

    public String getLdrLName() {
        return ldrLName;
    }

    public void setLdrLName(String ldrLName) {
        this.ldrLName = ldrLName;
    }

    public String getLdrSchlYr() {
        return ldrSchlYr;
    }

    public void setLdrSchlYr(String ldrSchlYr) {
        this.ldrSchlYr = ldrSchlYr;
    }

    public String getLdrTitle() {
        return ldrTitle;
    }

    public void setLdrTitle(String ldrTitle) {
        this.ldrTitle = ldrTitle;
    }
    
    
}
