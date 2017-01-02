/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.uniza.fri.pds.spotreba.energie.domain;

import java.io.Serializable;

public class SeZamestnanec implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idZamestnanca;
    private byte[] foto;
    private String rodCislo;
    private int idRegionu;

    public int getIdZamestnanca() {
        return idZamestnanca;
    }

    public void setIdZamestnanca(int idZamestnanca) {
        this.idZamestnanca = idZamestnanca;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
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
