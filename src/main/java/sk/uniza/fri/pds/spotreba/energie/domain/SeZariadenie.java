/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.uniza.fri.pds.spotreba.energie.domain;

import java.io.Serializable;

public class SeZariadenie implements Serializable {

    private static final long serialVersionUID = 1L;
    private int cisZariadenia;
    private int spotreba;
    private int typ;

    public int getCisZariadenia() {
        return cisZariadenia;
    }

    public void setCisZariadenia(int cisZariadenia) {
        this.cisZariadenia = cisZariadenia;
    }

    public int getSpotreba() {
        return spotreba;
    }

    public void setSpotreba(int spotreba) {
        this.spotreba = spotreba;
    }

    public int getTyp() {
        return typ;
    }

    public void setTyp(int typ) {
        this.typ = typ;
    }

}
