/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.uniza.fri.pds.spotreba.energie.domain;

import java.io.Serializable;
import org.metawidget.inspector.annotation.UiComesAfter;
import org.metawidget.inspector.annotation.UiHidden;
import sk.uniza.fri.pds.spotreba.energie.gui.DisplayPriority;

public class SeAdresa implements Serializable {

    private static final long serialVersionUID = 1L;
    private int idAdresy;
    private String cislo;
    private String ulica;
    private int idMesta;

    @DisplayPriority(priority = 1)

    @UiHidden
    public int getIdAdresy() {
        return idAdresy;
    }

    public void setIdAdresy(int idAdresy) {
        this.idAdresy = idAdresy;
    }

    @UiComesAfter(value = "idAdresy")
    public String getCislo() {
        return cislo;
    }

    public void setCislo(String cislo) {
        this.cislo = cislo;
    }

    @UiComesAfter(value = "idAdresy")
    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    @UiComesAfter(value = "idAdresy")
    public int getIdMesta() {
        return idMesta;
    }

    public void setIdMesta(int idMesta) {
        this.idMesta = idMesta;
    }

}
