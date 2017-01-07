/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.uniza.fri.pds.spotreba.energie.service.util;

import java.util.Date;
import org.metawidget.inspector.annotation.UiComesAfter;
import sk.uniza.fri.pds.spotreba.energie.domain.SeOdberatel;
import sk.uniza.fri.pds.spotreba.energie.domain.util.MeraciaVelicina;

public class StatistikaSpotriebParams {

    private SeOdberatel.TypOdberatela typOdberatela;
    private SeOdberatel.KategoriaOdberatela kategoriaOdberatela;
    private int idRegionu;
    private MeraciaVelicina velicina;
    private Date datumOd;
    private Date datumDo;

    public SeOdberatel.TypOdberatela getTypOdberatela() {
        return typOdberatela;
    }

    public void setTypOdberatela(SeOdberatel.TypOdberatela typOdberatela) {
        this.typOdberatela = typOdberatela;
    }

    @UiComesAfter("typOdberatela")
    public SeOdberatel.KategoriaOdberatela getKategoriaOdberatela() {
        return kategoriaOdberatela;
    }

    public void setKategoriaOdberatela(SeOdberatel.KategoriaOdberatela kategoriaOdberatela) {
        this.kategoriaOdberatela = kategoriaOdberatela;
    }

    @UiComesAfter("kategoriaOdberatela")
    public int getIdRegionu() {
        return idRegionu;
    }

    public void setIdRegionu(int idRegionu) {
        this.idRegionu = idRegionu;
    }

    @UiComesAfter("idRegionu")
    public MeraciaVelicina getVelicina() {
        return velicina;
    }

    public void setVelicina(MeraciaVelicina velicina) {
        this.velicina = velicina;
    }

    @UiComesAfter("velicina")
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

}
