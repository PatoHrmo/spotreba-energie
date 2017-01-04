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
import sk.uniza.fri.pds.spotreba.energie.domain.SeOdpis;

public class SeOdpisService implements SeService<SeOdpis> {

    private static volatile SeOdpisService instance;

    private SeOdpisService() {

    }

    @Override
    public void create(SeOdpis object) {
        try (Connection connection = OracleJDBCConnector.getConnection();) {
            CallableStatement stmnt = connection.prepareCall("BEGIN INSERT_SE_ODPIS(?, ?, ?, ?); END;");
            stmnt.setDate(1, Utils.utilDateToSqlDate(object.getDatumOdpisu()));
            stmnt.setInt(2, object.getCisZariadenia());
            stmnt.setInt(3, object.getIdZamestanca());
            stmnt.setInt(4, object.getStav());
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<SeOdpis> findAll() {
        try (Connection connection = OracleJDBCConnector.getConnection();) {
            CallableStatement stmnt = connection.prepareCall("SELECT * FROM SE_ODPIS ORDER BY DATUM_ODPISU ASC");
            ResultSet result = stmnt.executeQuery();
            List<SeOdpis> output = new LinkedList<>();
            while (result.next()) {
                SeOdpis o = new SeOdpis();
                o.setCisZariadenia(result.getInt("CIS_ZARIADENIA"));
                o.setDatumOdpisu(result.getDate("DATUM_ODPISU"));
                o.setIdZamestanca(result.getInt("ID_ZAMESTNANCA"));
                o.setStav(result.getInt("STAV"));
                output.add(o);
            }
            return output;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(SeOdpis object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(SeOdpis object) {
        throw new RuntimeException("Pre túto tabuľku bola táto funkcionalita zablokovaná!");
    }

    public static synchronized SeOdpisService getInstance() {
        if (instance == null) {
            instance = new SeOdpisService();
        }
        return instance;
    }

}
