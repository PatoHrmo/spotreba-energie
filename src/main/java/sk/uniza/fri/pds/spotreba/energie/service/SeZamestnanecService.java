/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.uniza.fri.pds.spotreba.energie.service;

import java.util.List;
import sk.uniza.fri.pds.spotreba.energie.domain.SeZamestnanec;

public class SeZamestnanecService implements SeService<SeZamestnanec> {

    private static volatile SeZamestnanecService instance;

    private SeZamestnanecService() {

    }

    @Override
    public void create(SeZamestnanec object) {

    }

    @Override
    public List<SeZamestnanec> findAll() {

        return null;
    }

    @Override
    public void update(SeZamestnanec object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(SeZamestnanec object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static synchronized SeZamestnanecService getInstance() {
        if (instance == null) {
            instance = new SeZamestnanecService();
        }
        return instance;
    }

}
