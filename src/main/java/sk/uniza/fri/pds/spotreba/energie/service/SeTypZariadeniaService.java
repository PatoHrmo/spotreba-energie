/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.uniza.fri.pds.spotreba.energie.service;

import java.util.List;
import sk.uniza.fri.pds.spotreba.energie.domain.SeTypZariadenia;

public class SeTypZariadeniaService implements SeService<SeTypZariadenia> {

    private static volatile SeTypZariadeniaService instance;

    private SeTypZariadeniaService() {

    }

    @Override
    public void create(SeTypZariadenia object) {

    }

    @Override
    public List<SeTypZariadenia> findAll() {
        return null;
    }

    @Override
    public void update(SeTypZariadenia object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(SeTypZariadenia object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static synchronized SeTypZariadeniaService getInstance() {
        if (instance == null) {
            instance = new SeTypZariadeniaService();
        }
        return instance;
    }

}
