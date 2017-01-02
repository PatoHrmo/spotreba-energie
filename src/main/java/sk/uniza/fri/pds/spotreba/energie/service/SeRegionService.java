/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.uniza.fri.pds.spotreba.energie.service;

import java.util.List;
import sk.uniza.fri.pds.spotreba.energie.domain.SeRegion;

public class SeRegionService implements SeService<SeRegion> {

    private static volatile SeRegionService instance;

    private SeRegionService() {

    }

    @Override
    public void create(SeRegion object) {

    }

    @Override
    public List<SeRegion> findAll() {

        return null;
    }

    @Override
    public void update(SeRegion object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(SeRegion object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static synchronized SeRegionService getInstance() {
        if (instance == null) {
            instance = new SeRegionService();
        }
        return instance;
    }

}
