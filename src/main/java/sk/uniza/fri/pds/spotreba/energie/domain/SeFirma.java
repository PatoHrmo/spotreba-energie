/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.uniza.fri.pds.spotreba.energie.domain;

import java.io.Serializable;

public class SeFirma implements Serializable {

    private static final long serialVersionUID = 1L;
    private String ico;
    private String nazovFirmy;
    private int idAdresy;

    public String getIco() {
        return ico;
    }

    public void setIco(String ico) {
        this.ico = ico;
    }

    public String getNazovFirmy() {
        return nazovFirmy;
    }

    public void setNazovFirmy(String nazovFirmy) {
        this.nazovFirmy = nazovFirmy;
    }

    public int getIdAdresy() {
        return idAdresy;
    }

    public void setIdAdresy(int idAdresy) {
        this.idAdresy = idAdresy;
    }

}
