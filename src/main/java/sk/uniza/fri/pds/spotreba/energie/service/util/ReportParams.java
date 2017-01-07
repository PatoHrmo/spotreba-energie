/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.uniza.fri.pds.spotreba.energie.service.util;

import java.io.File;

public class ReportParams {

    private int idOdberatela;
    private File subor;

    public int getIdOdberatela() {
        return idOdberatela;
    }

    public void setIdOdberatela(int idOdberatela) {
        this.idOdberatela = idOdberatela;
    }

    public File getSubor() {
        return subor;
    }

    public void setSubor(File subor) {
        this.subor = subor;
    }

}
