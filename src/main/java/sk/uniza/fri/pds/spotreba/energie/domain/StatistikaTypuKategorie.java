/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.uniza.fri.pds.spotreba.energie.domain;

import sk.uniza.fri.pds.spotreba.energie.gui.utils.DisplayPriority;

public class StatistikaTypuKategorie {

    private String typ;
    private String kategoria;
    private double minimalnaSpotreba;
    private int mesiacMinimalnejSpotreby;
    private double maximalnaSpotreba;
    private int mesiacMaximalnejSpotreby;
    private double priemernaSpotreba;

    @DisplayPriority(priority = 1)
    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    @DisplayPriority(priority = 2)
    public String getKategoria() {
        return kategoria;
    }

    public void setKategoria(String kategoria) {
        this.kategoria = kategoria;
    }

    @DisplayPriority(priority = 3)
    public double getMinimalnaSpotreba() {
        return minimalnaSpotreba;
    }

    public void setMinimalnaSpotreba(double minimalnaSpotreba) {
        this.minimalnaSpotreba = minimalnaSpotreba;
    }

    @DisplayPriority(priority = 4)
    public int getMesiacMinimalnejSpotreby() {
        return mesiacMinimalnejSpotreby;
    }

    public void setMesiacMinimalnejSpotreby(int mesiacMinimalnejSpotreby) {
        this.mesiacMinimalnejSpotreby = mesiacMinimalnejSpotreby;
    }

    @DisplayPriority(priority = 5)
    public double getMaximalnaSpotreba() {
        return maximalnaSpotreba;
    }

    public void setMaximalnaSpotreba(double maximalnaSpotreba) {
        this.maximalnaSpotreba = maximalnaSpotreba;
    }

    @DisplayPriority(priority = 6)
    public int getMesiacMaximalnejSpotreby() {
        return mesiacMaximalnejSpotreby;
    }

    public void setMesiacMaximalnejSpotreby(int mesiacMaximalnejSpotreby) {
        this.mesiacMaximalnejSpotreby = mesiacMaximalnejSpotreby;
    }

    @DisplayPriority(priority = 7)
    public double getPriemernaSpotreba() {
        return priemernaSpotreba;
    }

    public void setPriemernaSpotreba(double priemernaSpotreba) {
        this.priemernaSpotreba = priemernaSpotreba;
    }

}
