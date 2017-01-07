/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.uniza.fri.pds.spotreba.energie.service;

import sk.uniza.fri.pds.spotreba.energie.gui.utils.DisplayPriority;

public class NajminajucejsiSpotrebitel {

    private String meno;
    private int cisloSpotrebitela;

    public String getMeno() {
        return meno;
    }

    public void setMeno(String meno) {
        this.meno = meno;
    }

    @DisplayPriority(priority = 1)
    public int getCisloSpotrebitela() {
        return cisloSpotrebitela;
    }

    public void setCisloOdberatela(int cisloSpotrebitela) {
        this.cisloSpotrebitela = cisloSpotrebitela;
    }

}
