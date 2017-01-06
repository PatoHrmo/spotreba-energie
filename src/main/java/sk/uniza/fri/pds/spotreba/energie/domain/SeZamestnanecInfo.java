/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.uniza.fri.pds.spotreba.energie.domain;

import sk.uniza.fri.pds.spotreba.energie.gui.utils.DisplayPriority;

public class SeZamestnanecInfo {

    private int idZamestnanca;
    private String meno;
    private String priezvisko;

    @DisplayPriority(priority = 1)
    public int getIdZamestnanca() {
        return idZamestnanca;
    }

    public void setIdZamestnanca(int idZamestnanca) {
        this.idZamestnanca = idZamestnanca;
    }

    @DisplayPriority(priority = 2)
    public String getMeno() {
        return meno;
    }

    public void setMeno(String meno) {
        this.meno = meno;
    }

    @DisplayPriority(priority = 3)
    public String getPriezvisko() {
        return priezvisko;
    }

    public void setPriezvisko(String priezvisko) {
        this.priezvisko = priezvisko;
    }

}
