/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.uniza.fri.pds.spotreba.energie.service.util;

import java.util.Date;
import org.metawidget.inspector.annotation.UiComesAfter;
import sk.uniza.fri.pds.spotreba.energie.domain.util.MeraciaVelicina;

public class NajminajucejsiSpotrebiteliaParams {

    private Date datumOd;
    private Date datumDo;
    private MeraciaVelicina velicina;

    public Date getDatumOd() {
        return datumOd;
    }

    public void setDatumOd(Date datumOd) {
        this.datumOd = datumOd;
    }

    @UiComesAfter("datumOd")
    public Date getDatumDo() {
        return datumDo;
    }

    public void setDatumDo(Date datumDo) {
        this.datumDo = datumDo;
    }

    @UiComesAfter("datumDo")
    public MeraciaVelicina getVelicina() {
        return velicina;
    }

    public void setVelicina(MeraciaVelicina velicina) {
        this.velicina = velicina;
    }

}
