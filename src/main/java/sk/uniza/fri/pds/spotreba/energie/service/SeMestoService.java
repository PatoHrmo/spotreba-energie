/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.uniza.fri.pds.spotreba.energie.service;

import java.util.List;
import sk.uniza.fri.pds.spotreba.energie.domain.SeMesto;

public class SeMestoService implements SeService<SeMesto> {

    private static volatile SeMestoService instance;

    private SeMestoService() {

    }

    @Override
    public void create(SeMesto object) {

    }

    @Override
    public List<SeMesto> findAll() {

        return null;
    }

    @Override
    public void update(SeMesto object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(SeMesto object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static synchronized SeMestoService getInstance() {
        if (instance == null) {
            instance = new SeMestoService();
        }
        return instance;
    }

}
