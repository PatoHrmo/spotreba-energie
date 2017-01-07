/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.uniza.fri.pds.spotreba.energie.domain;

import sk.uniza.fri.pds.spotreba.energie.gui.utils.DisplayPriority;
import sk.uniza.fri.pds.spotreba.energie.gui.utils.TableCollumnName;

public class StatistikaServisov {

    private int cisloZariadenia;
    private int pocetServisovDo5Rokov;
    private int pocetServisovTypu0;
    private int pocetServisouTypu1;
    private int pocetServisouTypu2;

    @DisplayPriority(priority = 1)
    public int getCisloZariadenia() {
        return cisloZariadenia;
    }

    public void setCisloZariadenia(int cisloZariadenia) {
        this.cisloZariadenia = cisloZariadenia;
    }

    @DisplayPriority(priority = 2)
    @TableCollumnName(name = "Počet servisov do 5 rokov")
    public int getPocetServisovDo5Rokov() {
        return pocetServisovDo5Rokov;
    }

    public void setPocetServisovDo5Rokov(int pocetServisovDo5Rokov) {
        this.pocetServisovDo5Rokov = pocetServisovDo5Rokov;
    }

    @TableCollumnName(name = "Počet servisov (diagnostika)")
    public int getPocetServisovTypu0() {
        return pocetServisovTypu0;
    }

    public void setPocetServisovTypu0(int pocetServisovTypu0) {
        this.pocetServisovTypu0 = pocetServisovTypu0;
    }

    @TableCollumnName(name = "Počet servisov (oprava)")
    public int getPocetServisovTypu1() {
        return pocetServisouTypu1;
    }

    public void setPocetServisovTypu1(int pocetServisouTypu1) {
        this.pocetServisouTypu1 = pocetServisouTypu1;
    }

    @TableCollumnName(name = "Počet servisov (nastavenie)")
    public int getPocetServisovTypu2() {
        return pocetServisouTypu2;
    }

    public void setPocetServisovTypu2(int pocetServisouTypu2) {
        this.pocetServisouTypu2 = pocetServisouTypu2;
    }

}
