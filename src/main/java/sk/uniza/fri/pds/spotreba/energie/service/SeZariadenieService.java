/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.uniza.fri.pds.spotreba.energie.service;

import java.util.List;
import sk.uniza.fri.pds.spotreba.energie.domain.SeZariadenie;

public class SeZariadenieService implements SeService<SeZariadenie> {

    private static volatile SeZariadenieService instance;

    private SeZariadenieService() {

    }

    @Override
    public void create(SeZariadenie object) {

    }

    @Override
    public List<SeZariadenie> findAll() {

        return null;
    }

    @Override
    public void update(SeZariadenie object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(SeZariadenie object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static synchronized SeZariadenieService getInstance() {
        if (instance == null) {
            instance = new SeZariadenieService();
        }
        return instance;
    }

}
