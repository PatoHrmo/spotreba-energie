/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.uniza.fri.pds.spotreba.energie.service;

import java.util.List;
import sk.uniza.fri.pds.spotreba.energie.domain.SeOdberatel;

public class SeOdberatelService implements SeService<SeOdberatel> {

    private static volatile SeOdberatelService instance;

    private SeOdberatelService() {

    }

    @Override
    public void create(SeOdberatel object) {

    }

    @Override
    public List<SeOdberatel> findAll() {

        return null;
    }

    @Override
    public void update(SeOdberatel object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(SeOdberatel object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static synchronized SeOdberatelService getInstance() {
        if (instance == null) {
            instance = new SeOdberatelService();
        }
        return instance;
    }

}
