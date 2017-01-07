/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.uniza.fri.pds.spotreba.energie.domain;

import java.util.Date;
import sk.uniza.fri.pds.spotreba.energie.gui.utils.DisplayPriority;

public class CelkovaStatistika {

    private int minimalnaSpotreba;
    private Date mesiacMinimalnejSpotreby;
    private int maximalnaSpotreba;
    private Date mesiacMaximalnejSpotreby;

    @DisplayPriority(priority = 1)
    public int getMinimalnaSpotreba() {
        return minimalnaSpotreba;
    }

    public void setMinimalnaSpotreba(int minimalnaSpotreba) {
        this.minimalnaSpotreba = minimalnaSpotreba;
    }

    @DisplayPriority(priority = 2)
    public Date getMesiacMinimalnejSpotreby() {
        return mesiacMinimalnejSpotreby;
    }

    public void setMesiacMinimalnejSpotreby(Date mesiacMinimalnejSpotreby) {
        this.mesiacMinimalnejSpotreby = mesiacMinimalnejSpotreby;
    }

    @DisplayPriority(priority = 3)
    public int getMaximalnaSpotreba() {
        return maximalnaSpotreba;
    }

    public void setMaximalnaSpotreba(int maximalnaSpotreba) {
        this.maximalnaSpotreba = maximalnaSpotreba;
    }

    @DisplayPriority(priority = 4)
    public Date getMesiacMaximalnejSpotreby() {
        return mesiacMaximalnejSpotreby;
    }

    public void setMesiacMaximalnejSpotreby(Date mesiacMaximalnejSpotreby) {
        this.mesiacMaximalnejSpotreby = mesiacMaximalnejSpotreby;
    }

}
