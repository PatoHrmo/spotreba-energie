/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.uniza.fri.pds.spotreba.energie.service;

import java.util.List;
import sk.uniza.fri.pds.spotreba.energie.domain.SeOdpis;

public class SeOdpisService implements SeService<SeOdpis> {

    private static volatile SeOdpisService instance;

    private SeOdpisService() {

    }

    @Override
    public void create(SeOdpis object) {

    }

    @Override
    public List<SeOdpis> findAll() {

        return null;
    }

    @Override
    public void update(SeOdpis object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(SeOdpis object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static synchronized SeOdpisService getInstance() {
        if (instance == null) {
            instance = new SeOdpisService();
        }
        return instance;
    }

}
