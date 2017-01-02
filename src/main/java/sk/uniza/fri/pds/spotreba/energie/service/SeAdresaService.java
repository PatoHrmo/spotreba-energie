/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.uniza.fri.pds.spotreba.energie.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import sk.uniza.fri.pds.spotreba.energie.OracleJDBCConnector;
import sk.uniza.fri.pds.spotreba.energie.domain.SeAdresa;

public class SeAdresaService implements SeService<SeAdresa> {

    private static volatile SeAdresaService instance;

    private SeAdresaService() {

    }

    @Override
    public void create(SeAdresa object) {

    }

    @Override
    public List<SeAdresa> findAll() {
        try (Connection connection = OracleJDBCConnector.getConnection();) {
            CallableStatement stmnt = connection.prepareCall("SELECT * FROM SE_ADRESA");
            ResultSet result = stmnt.executeQuery();
            List<SeAdresa> output = new LinkedList<>();
            while (result.next()) {
                SeAdresa o = new SeAdresa();
                o.setIdAdresy(result.getInt("ID_ADRESY"));
                o.setIdMesta(result.getInt("ID_MESTA"));
                o.setCislo(result.getString("CISLO"));
                o.setUlica(result.getString("ULICA"));
                output.add(o);
            }
            return output;
        } catch (SQLException e) {

        }
        return null;
    }

    @Override
    public void update(SeAdresa object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(SeAdresa object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static synchronized SeAdresaService getInstance() {
        if (instance == null) {
            instance = new SeAdresaService();
        }
        return instance;
    }

}
