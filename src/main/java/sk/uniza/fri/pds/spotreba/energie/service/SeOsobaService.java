/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.uniza.fri.pds.spotreba.energie.service;

import java.util.List;
import sk.uniza.fri.pds.spotreba.energie.domain.SeOsoba;

public class SeOsobaService implements SeService<SeOsoba> {

    private static volatile SeOsobaService instance;

    private SeOsobaService() {

    }

    @Override
    public void create(SeOsoba object) {

    }

    @Override
    public List<SeOsoba> findAll() {
        return null;
    }

    @Override
    public void update(SeOsoba object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(SeOsoba object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static synchronized SeOsobaService getInstance() {
        if (instance == null) {
            instance = new SeOsobaService();
        }
        return instance;
    }

}
