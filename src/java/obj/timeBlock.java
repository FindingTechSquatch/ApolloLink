/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obj;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author tyleryork
 */
public class timeBlock implements Serializable {
    private LocalDateTime strt_Tme;
    private LocalDateTime end_Tme;
    private boolean taken;

    public timeBlock() {
    }

    public timeBlock(LocalDateTime strt_Tme, LocalDateTime end_Tme) {
        this.strt_Tme = strt_Tme;
        this.end_Tme = end_Tme;
    }

    public timeBlock(LocalDateTime strt_Tme, LocalDateTime end_Tme, boolean taken) {
        this.strt_Tme = strt_Tme;
        this.end_Tme = end_Tme;
        this.taken = taken;
    }

    public LocalDateTime getStrt_Tme() {
        return strt_Tme;
    }

    public void setStrt_Tme(LocalDateTime strt_Tme) {
        this.strt_Tme = strt_Tme;
    }

    public LocalDateTime getEnd_Tme() {
        return end_Tme;
    }

    public void setEnd_Tme(LocalDateTime end_Tme) {
        this.end_Tme = end_Tme;
    }

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    @Override
    public String toString() {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("hh:mma");
        return this.strt_Tme.format(f)+" - "+this.end_Tme.format(f);
    }
    
    
}
