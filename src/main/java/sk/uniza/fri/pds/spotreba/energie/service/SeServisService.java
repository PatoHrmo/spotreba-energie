/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.uniza.fri.pds.spotreba.energie.service;

import java.util.List;
import sk.uniza.fri.pds.spotreba.energie.domain.SeServis;

public class SeServisService implements SeService<SeServis> {

    private static volatile SeServisService instance;

    private SeServisService() {

    }

    @Override
    public void create(SeServis object) {
        System.out.println("");
    }

    @Override
    public List<SeServis> findAll() {

        return null;
    }

    @Override
    public void update(SeServis object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(SeServis object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static synchronized SeServisService getInstance() {
        if (instance == null) {
            instance = new SeServisService();
        }
        return instance;
    }

}
