/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.uniza.fri.pds.spotreba.energie.domain;

import java.io.Serializable;
import java.util.Date;

public class SeHistoria implements Serializable {

    private static final long serialVersionUID = 1L;
    private int cisloOdberatela;
    private int cisZariadenia;
    private Date datumInstalacie;
    private Date datumOdobratia;

    public int getCisloOdberatela() {
        return cisloOdberatela;
    }

    public void setCisloOdberatela(int cisloOdberatela) {
        this.cisloOdberatela = cisloOdberatela;
    }

    public int getCisZariadenia() {
        return cisZariadenia;
    }

    public void setCisZariadenia(int cisZariadenia) {
        this.cisZariadenia = cisZariadenia;
    }

    public Date getDatumInstalacie() {
        return datumInstalacie;
    }

    public void setDatumInstalacie(Date datumInstalacie) {
        this.datumInstalacie = datumInstalacie;
    }

    public Date getDatumOdobratia() {
        return datumOdobratia;
    }

    public void setDatumOdobratia(Date datumOdobratia) {
        this.datumOdobratia = datumOdobratia;
    }

}
