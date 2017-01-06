/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.uniza.fri.pds.spotreba.energie.domain;

import java.io.Serializable;
import org.metawidget.inspector.annotation.UiComesAfter;
import sk.uniza.fri.pds.spotreba.energie.gui.utils.DisplayPriority;

public class SeOsoba implements Serializable {

    private static final long serialVersionUID = 1L;
    private String rodCislo;
    private String meno;
    private String priezvisko;
    private int idAdresy;

    @DisplayPriority(priority = 1)
    public String getRodCislo() {
        return rodCislo;
    }

    public void setRodCislo(String rodCislo) {
        this.rodCislo = rodCislo;
    }

    @UiComesAfter(value = "rodCislo")
    @DisplayPriority(priority = 2)
    public String getMeno() {
        return meno;
    }

    public void setMeno(String meno) {
        this.meno = meno;
    }

    @UiComesAfter(value = "meno")
    @DisplayPriority(priority = 3)
    public String getPriezvisko() {
        return priezvisko;
    }

    public void setPriezvisko(String priezvisko) {
        this.priezvisko = priezvisko;
    }

    @UiComesAfter(value = "priezvisko")
    public int getIdAdresy() {
        return idAdresy;
    }

    public void setIdAdresy(int idAdresy) {
        this.idAdresy = idAdresy;
    }

}
