/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.uniza.fri.pds.spotreba.energie.domain;

import java.io.Serializable;
import org.metawidget.inspector.annotation.UiHidden;
import sk.uniza.fri.pds.spotreba.energie.gui.utils.DisplayPriority;

public class SeRegion implements Serializable {

    private static final long serialVersionUID = 1L;
    private int idRegionu;
    private String nazov;

    @DisplayPriority(priority = 1)
    @UiHidden
    public int getIdRegionu() {
        return idRegionu;
    }

    public void setIdRegionu(int idRegionu) {
        this.idRegionu = idRegionu;
    }

    public String getNazov() {
        return nazov;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }

}
