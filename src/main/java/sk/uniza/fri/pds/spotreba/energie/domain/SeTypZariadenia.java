/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.uniza.fri.pds.spotreba.energie.domain;

import java.io.Serializable;
import org.metawidget.inspector.annotation.UiHidden;
import sk.uniza.fri.pds.spotreba.energie.domain.util.MeraciaVelicina;
import sk.uniza.fri.pds.spotreba.energie.gui.utils.DisplayPriority;

public class SeTypZariadenia implements Serializable {

    private static final long serialVersionUID = 1L;
    private int typ;
    private MeraciaVelicina meraciaVelicina;
    private int cisloModelu;
    private String vyrobca;

    @DisplayPriority(priority = 1)
    @UiHidden
    public int getTyp() {
        return typ;
    }

    public void setTyp(int typ) {
        this.typ = typ;
    }

    public MeraciaVelicina getMeraciaVelicina() {
        return meraciaVelicina;
    }

    public void setMeraciaVelicina(MeraciaVelicina meraciaVelicina) {
        this.meraciaVelicina = meraciaVelicina;
    }

    public int getCisloModelu() {
        return cisloModelu;
    }

    public void setCisloModelu(int cisloModelu) {
        this.cisloModelu = cisloModelu;
    }

    public String getVyrobca() {
        return vyrobca;
    }

    public void setVyrobca(String vyrobca) {
        this.vyrobca = vyrobca;
    }

}
