/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.uniza.fri.pds.spotreba.energie.domain;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import org.metawidget.inspector.annotation.UiHidden;
import sk.uniza.fri.pds.spotreba.energie.gui.DisplayPriority;
import sk.uniza.fri.pds.spotreba.energie.gui.TableHidden;

public class SeZamestnanec implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idZamestnanca;
    private BufferedImage foto;
    private String rodCislo;
    private int idRegionu;

    @DisplayPriority(priority = 1)
    @UiHidden
    public int getIdZamestnanca() {
        return idZamestnanca;
    }

    public void setIdZamestnanca(int idZamestnanca) {
        this.idZamestnanca = idZamestnanca;
    }

    @TableHidden
    public BufferedImage getFoto() {
        return foto;
    }

    public void setFoto(BufferedImage foto) {
        this.foto = foto;
    }

    public String getRodCislo() {
        return rodCislo;
    }

    public void setRodCislo(String rodCislo) {
        this.rodCislo = rodCislo;
    }

    public int getIdRegionu() {
        return idRegionu;
    }

    public void setIdRegionu(int idRegionu) {
        this.idRegionu = idRegionu;
    }

}
