/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.uniza.fri.pds.spotreba.energie.service;

import java.util.List;
import sk.uniza.fri.pds.spotreba.energie.domain.SeFirma;

public class SeFirmaService implements SeService<SeFirma> {

    private static volatile SeFirmaService instance;

    private SeFirmaService() {

    }

    @Override
    public void create(SeFirma object) {

    }

    @Override
    public List<SeFirma> findAll() {

        return null;
    }

    @Override
    public void update(SeFirma object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(SeFirma object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static synchronized SeFirmaService getInstance() {
        if (instance == null) {
            instance = new SeFirmaService();
        }
        return instance;
    }

}
