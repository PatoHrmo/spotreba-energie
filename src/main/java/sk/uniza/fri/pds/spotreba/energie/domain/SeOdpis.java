/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.uniza.fri.pds.spotreba.energie.domain;

import java.io.Serializable;
import java.util.Date;

public class SeOdpis implements Serializable {

    private static final long serialVersionUID = 1L;
    private Date datumOdpisu;
    private int cisZariadenia;
    private int stav;
    private int idZamestanca;

    public Date getDatumOdpisu() {
        return datumOdpisu;
    }

    public void setDatumOdpisu(Date datumOdpisu) {
        this.datumOdpisu = datumOdpisu;
    }

    public int getCisZariadenia() {
        return cisZariadenia;
    }

    public void setCisZariadenia(int cisZariadenia) {
        this.cisZariadenia = cisZariadenia;
    }

    public int getStav() {
        return stav;
    }

    public void setStav(int stav) {
        this.stav = stav;
    }

    public int getIdZamestanca() {
        return idZamestanca;
    }

    public void setIdZamestanca(int idZamestanca) {
        this.idZamestanca = idZamestanca;
    }

}
