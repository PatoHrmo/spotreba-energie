/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.uniza.fri.pds.spotreba.energie.domain;

import java.io.Serializable;
import org.metawidget.inspector.annotation.UiHidden;
import sk.uniza.fri.pds.spotreba.energie.gui.DisplayPriority;

public class SeOdberatel implements Serializable {

    private static final long serialVersionUID = 1L;
    private int cisloOdberatela;
    private Character typ;
    private Character kategoria;
    private String ico;
    private String rodCislo;

    @DisplayPriority(priority = 1)
    @UiHidden
    public int getCisloOdberatela() {
        return cisloOdberatela;
    }

    public Character getTyp() {
        return typ;
    }

    public Character getKategoria() {
        return kategoria;
    }

    public String getIco() {
        return ico;
    }

    public String getRodCislo() {
        return rodCislo;
    }

    public void setCisloOdberatela(int cisloOdberatela) {
        this.cisloOdberatela = cisloOdberatela;
    }

    public void setTyp(Character typ) {
        this.typ = typ;
    }

    public void setKategoria(Character kategoria) {
        this.kategoria = kategoria;
    }

    public void setIco(String ico) {
        this.ico = ico;
    }

    public void setRodCislo(String rodCislo) {
        this.rodCislo = rodCislo;
    }

}
