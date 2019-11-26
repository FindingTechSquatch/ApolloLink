/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obj;

import java.io.Serializable;
import java.time.*;

/**
 *
 * @author tyleryork
 */
public class Registration implements Serializable{
    private int RID;
    private LocalDateTime regDteTm;
    private String type;
    private LocalDateTime selDteTm;
    private String addlStff;
    private int bus;
    private int truck;
    
    private String perfTitle;
    private String song1;
    private String song2;
    private String song3;
    private String song4;
    private String song5;
    private String preAnnounce;
    private String postAnnounce;

    public Registration() {
    }

    public Registration(int RID, LocalDateTime regDteTm, String type, LocalDateTime selDteTm, String addlStff, int bus, int truck, String perfTitle, String song1, String song2, String song3, String song4, String song5, String preAnnounce, String postAnnounce) {
        this.RID = RID;
        this.regDteTm = regDteTm;
        this.type = type;
        this.selDteTm = selDteTm;
        this.addlStff = addlStff;
        this.bus = bus;
        this.truck = truck;
        this.perfTitle = perfTitle;
        this.song1 = song1;
        this.song2 = song2;
        this.song3 = song3;
        this.song4 = song4;
        this.song5 = song5;
        this.preAnnounce = preAnnounce;
        this.postAnnounce = postAnnounce;
    }

    
    
    public int getRID() {
        return RID;
    }

    public void setRID(int RID) {
        this.RID = RID;
    }

    public LocalDateTime getRegDteTm() {
        return regDteTm;
    }

    public void setRegDteTm(LocalDateTime regDteTm) {
        this.regDteTm = regDteTm;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getSelDteTm() {
        return selDteTm;
    }

    public void setSelDteTm(LocalDateTime selDteTm) {
        this.selDteTm = selDteTm;
    }

    public String getAddlStff() {
        return addlStff;
    }

    public void setAddlStff(String addlStff) {
        this.addlStff = addlStff;
    }

    public int getBus() {
        return bus;
    }

    public void setBus(int bus) {
        this.bus = bus;
    }

    public int getTruck() {
        return truck;
    }

    public void setTruck(int truck) {
        this.truck = truck;
    }

    public String getPerfTitle() {
        return perfTitle;
    }

    public void setPerfTitle(String perfTitle) {
        this.perfTitle = perfTitle;
    }

    public String getSong1() {
        return song1;
    }

    public void setSong1(String song1) {
        this.song1 = song1;
    }

    public String getSong2() {
        return song2;
    }

    public void setSong2(String song2) {
        this.song2 = song2;
    }

    public String getSong3() {
        return song3;
    }

    public void setSong3(String song3) {
        this.song3 = song3;
    }

    public String getSong4() {
        return song4;
    }

    public void setSong4(String song4) {
        this.song4 = song4;
    }

    public String getSong5() {
        return song5;
    }

    public void setSong5(String song5) {
        this.song5 = song5;
    }

    public String getPreAnnounce() {
        return preAnnounce;
    }

    public void setPreAnnounce(String preAnnounce) {
        this.preAnnounce = preAnnounce;
    }

    public String getPostAnnounce() {
        return postAnnounce;
    }

    public void setPostAnnounce(String postAnnounce) {
        this.postAnnounce = postAnnounce;
    }
    
    
    
}
