/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.uniza.fri.pds.spotreba.energie.service.util;

import java.util.Date;
import org.metawidget.inspector.annotation.UiComesAfter;
import org.metawidget.inspector.annotation.UiRequired;
import sk.uniza.fri.pds.spotreba.energie.domain.util.MeraciaVelicina;

public class SpendingStatisticsParameters {

    private Date from;
    private Date to;
    private int customer;
    private MeraciaVelicina velicina;
    private Granularita granularita;

    @UiRequired
    public Date getDatumOd() {
        return from;
    }

    public void setDatumOd(Date from) {
        this.from = from;
    }

    @UiRequired
    @UiComesAfter("datumOd")
    public Date getDatumDo() {
        return to;
    }

    public void setDatumDo(Date to) {
        this.to = to;
    }

    @UiRequired
    public int getIdSpotrebitela() {
        return customer;
    }

    public void setIdSpotrebitela(int customer) {
        this.customer = customer;
    }

    @UiRequired
    public MeraciaVelicina getVelicina() {
        return velicina;
    }

    public void setVelicina(MeraciaVelicina velicina) {
        this.velicina = velicina;
    }

    @UiRequired
    public Granularita getGranularita() {
        return granularita;
    }

    public void setGranularita(Granularita granularita) {
        this.granularita = granularita;
    }

    public enum Granularita {
        MESACNE(0), POLROCNE(1), ROCNE(2);

        public final int val;

        private Granularita(int val) {
            this.val = val;
        }

    }

}
