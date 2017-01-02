/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.uniza.fri.pds.spotreba.energie.service;

import java.util.List;
import sk.uniza.fri.pds.spotreba.energie.domain.SeHistoria;

public class SeHistoriaService implements SeService<SeHistoria> {

    private static volatile SeHistoriaService instance;

    private SeHistoriaService() {

    }

    @Override
    public void create(SeHistoria object) {
        System.out.println("");
    }

    @Override
    public List<SeHistoria> findAll() {

        return null;
    }

    @Override
    public void update(SeHistoria object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(SeHistoria object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static synchronized SeHistoriaService getInstance() {
        if (instance == null) {
            instance = new SeHistoriaService();
        }
        return instance;
    }

}
