/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.uniza.fri.pds.spotreba.energie.domain;

import java.io.Serializable;

public class SeMesto implements Serializable {

    private static final long serialVersionUID = 1L;
    private int idMesta;
    private String psc;
    private String nazov;
    private int idRegionu;

    public int getIdMesta() {
        return idMesta;
    }

    public void setIdMesta(int idMesta) {
        this.idMesta = idMesta;
    }

    public String getPsc() {
        return psc;
    }

    public void setPsc(String psc) {
        this.psc = psc;
    }

    public String getNazov() {
        return nazov;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }

    public int getIdRegionu() {
        return idRegionu;
    }

    public void setIdRegionu(int idRegionu) {
        this.idRegionu = idRegionu;
    }

}
