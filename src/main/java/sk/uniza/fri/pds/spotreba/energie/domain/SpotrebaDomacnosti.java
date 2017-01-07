/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.uniza.fri.pds.spotreba.energie.domain;

import sk.uniza.fri.pds.spotreba.energie.domain.util.MeraciaVelicina;
import sk.uniza.fri.pds.spotreba.energie.gui.utils.DisplayPriority;

public class SpotrebaDomacnosti {

    private int cisloOdberatela;
    private String meno;
    private MeraciaVelicina velicina;
    private double spotreba;
    private int pocetVymen;

    @DisplayPriority(priority = 1)
    public int getCisloOdberatela() {
        return cisloOdberatela;
    }

    public void setCisloOdberatela(int cisloOdberatela) {
        this.cisloOdberatela = cisloOdberatela;
    }

    @DisplayPriority(priority = 2)
    public String getMeno() {
        return meno;
    }

    public void setMeno(String meno) {
        this.meno = meno;
    }

    @DisplayPriority(priority = 3)
    public int getPocetVymen() {
        return pocetVymen;
    }

    public void setPocetVymen(int pocetVymen) {
        this.pocetVymen = pocetVymen;
    }

    @DisplayPriority(priority = 4)
    public MeraciaVelicina getVelicina() {
        return velicina;
    }

    public void setVelicina(MeraciaVelicina velicina) {
        this.velicina = velicina;
    }

    @DisplayPriority(priority = 5)
    public double getSpotreba() {
        return spotreba;
    }

    public void setSpotreba(double spotreba) {
        this.spotreba = spotreba;
    }

}
