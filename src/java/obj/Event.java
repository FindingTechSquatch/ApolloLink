/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obj;

import java.io.Serializable;
import java.time.*;
import java.util.ArrayList;

/**
 *
 * @author tyleryork
 */
public class Event implements Serializable{
    private int EID;
    private String name;
    private String host;
    private String type;
    private LocalDateTime strtDteTm;
    private LocalDateTime endDteTm;
    private int blckSize;
    
    private String locTitle;
    private String addr1;
    private String addr2;
    private String city;
    private String state;
    
    private LocalDate regEarlyStrtDte;
    private LocalDate regEarlyEndDte;
    private LocalDate regRegStrtDte;
    private LocalDate regRegEndDte;
    private LocalDate regLtStrtDte;
    private LocalDate regLtEndDte;
    
    private double regEarlyCst;
    private double regRegCst;
    private double regLtCst;

    private ArrayList<Registration> regs;

    public Event() {
    }

    public Event(int EID, String name, String host, String type, LocalDateTime strtDteTm, LocalDateTime endDteTm, int blckSize, String locTitle, String addr1, String addr2, String city, String state, LocalDate regEarlyStrtDte, LocalDate regEarlyEndDte, LocalDate regRegStrtDte, LocalDate regRegEndDte, LocalDate regLtStrtDte, LocalDate regLtEndDte, double regEarlyCst, double regRegCst, double regLtCst, ArrayList<Registration> regs) {
        this.EID = EID;
        this.name = name;
        this.host = host;
        this.type = type;
        this.strtDteTm = strtDteTm;
        this.endDteTm = endDteTm;
        this.blckSize = blckSize;
        this.locTitle = locTitle;
        this.addr1 = addr1;
        this.addr2 = addr2;
        this.city = city;
        this.state = state;
        this.regEarlyStrtDte = regEarlyStrtDte;
        this.regEarlyEndDte = regEarlyEndDte;
        this.regRegStrtDte = regRegStrtDte;
        this.regRegEndDte = regRegEndDte;
        this.regLtStrtDte = regLtStrtDte;
        this.regLtEndDte = regLtEndDte;
        this.regEarlyCst = regEarlyCst;
        this.regRegCst = regRegCst;
        this.regLtCst = regLtCst;
        this.regs = regs;
    }
    
    

    public int getEID() {
        return EID;
    }

    public void setEID(int EID) {
        this.EID = EID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getStrtDteTm() {
        return strtDteTm;
    }

    public void setStrtDteTm(LocalDateTime strtDteTm) {
        this.strtDteTm = strtDteTm;
    }

    public LocalDateTime getEndDteTm() {
        return endDteTm;
    }

    public void setEndDteTm(LocalDateTime endDteTm) {
        this.endDteTm = endDteTm;
    }

    public int getBlckSize() {
        return blckSize;
    }

    public void setBlckSize(int blckSize) {
        this.blckSize = blckSize;
    }

    public String getLocTitle() {
        return locTitle;
    }

    public void setLocTitle(String locTitle) {
        this.locTitle = locTitle;
    }

    public String getAddr1() {
        return addr1;
    }

    public void setAddr1(String addr1) {
        this.addr1 = addr1;
    }

    public String getAddr2() {
        return addr2;
    }

    public void setAddr2(String addr2) {
        this.addr2 = addr2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public LocalDate getRegEarlyStrtDte() {
        return regEarlyStrtDte;
    }

    public void setRegEarlyStrtDte(LocalDate regEarlyStrtDte) {
        this.regEarlyStrtDte = regEarlyStrtDte;
    }

    public LocalDate getRegEarlyEndDte() {
        return regEarlyEndDte;
    }

    public void setRegEarlyEndDte(LocalDate regEarlyEndDte) {
        this.regEarlyEndDte = regEarlyEndDte;
    }

    public LocalDate getRegRegStrtDte() {
        return regRegStrtDte;
    }

    public void setRegRegStrtDte(LocalDate regRegStrtDte) {
        this.regRegStrtDte = regRegStrtDte;
    }

    public LocalDate getRegRegEndDte() {
        return regRegEndDte;
    }

    public void setRegRegEndDte(LocalDate regRegEndDte) {
        this.regRegEndDte = regRegEndDte;
    }

    public LocalDate getRegLtStrtDte() {
        return regLtStrtDte;
    }

    public void setRegLtStrtDte(LocalDate regLtStrtDte) {
        this.regLtStrtDte = regLtStrtDte;
    }

    public LocalDate getRegLtEndDte() {
        return regLtEndDte;
    }

    public void setRegLtEndDte(LocalDate regLtEndDte) {
        this.regLtEndDte = regLtEndDte;
    }

    public double getRegEarlyCst() {
        return regEarlyCst;
    }

    public void setRegEarlyCst(double regEarlyCst) {
        this.regEarlyCst = regEarlyCst;
    }

    public double getRegRegCst() {
        return regRegCst;
    }

    public void setRegRegCst(double regRegCst) {
        this.regRegCst = regRegCst;
    }

    public double getRegLtCst() {
        return regLtCst;
    }

    public void setRegLtCst(double regLtCst) {
        this.regLtCst = regLtCst;
    }

    public ArrayList<Registration> getRegs() {
        return regs;
    }

    public void setRegs(ArrayList<Registration> regs) {
        this.regs = regs;
    }
    
    public String getFullDisplay() {
        return this.name + " - " + this.locTitle + " - " + this.city + ", " + this.state;
    }
    
    
}
