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
public class Group implements Serializable{
    
    private int GID;
    private String grpName;
    private String grpType;
    private int grpSize;
    private ArrayList<grpLeader> ldrs;
    private ArrayList<Registration> regs;

    public Group() {
    }

    public Group(int GID, String grpName, String grpType, int grpSize, ArrayList<grpLeader> ldrs, ArrayList<Registration> regs) {
        this.GID = GID;
        this.grpName = grpName;
        this.grpType = grpType;
        this.grpSize = grpSize;
        this.ldrs = ldrs;
        this.regs = regs;
    }

    
    public int getGID() {
        return GID;
    }

    public void setGID(int GID) {
        this.GID = GID;
    }

    public String getGrpName() {
        return grpName;
    }

    public void setGrpName(String grpName) {
        this.grpName = grpName;
    }

    public String getGrpType() {
        return grpType;
    }

    public void setGrpType(String grpType) {
        this.grpType = grpType;
    }

    public int getGrpSize() {
        return grpSize;
    }

    public void setGrpSize(int grpSize) {
        this.grpSize = grpSize;
    }

    public ArrayList<grpLeader> getLdrs() {
        return ldrs;
    }

    public void setLdrs(ArrayList<grpLeader> ldrs) {
        this.ldrs = ldrs;
    }

    public ArrayList<Registration> getRegs() {
        return regs;
    }

    public void setRegs(ArrayList<Registration> regs) {
        this.regs = regs;
    }
    
    
}
