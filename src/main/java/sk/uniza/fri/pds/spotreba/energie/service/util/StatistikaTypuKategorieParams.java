/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.uniza.fri.pds.spotreba.energie.service.util;

import sk.uniza.fri.pds.spotreba.energie.domain.util.MeraciaVelicina;

public class StatistikaTypuKategorieParams {

    private int rok;
    private MeraciaVelicina velicina;

    public int getRok() {
        return rok;
    }

    public void setRok(int rok) {
        this.rok = rok;
    }

    public MeraciaVelicina getVelicina() {
        return velicina;
    }

    public void setVelicina(MeraciaVelicina velicina) {
        this.velicina = velicina;
    }

}
