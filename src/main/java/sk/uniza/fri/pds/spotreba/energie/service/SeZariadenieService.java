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
import sk.uniza.fri.pds.spotreba.energie.domain.SeZariadenie;

public class SeZariadenieService implements SeService<SeZariadenie> {

    private static volatile SeZariadenieService instance;

    private SeZariadenieService() {

    }

    @Override
    public void create(SeZariadenie object) {
        try (Connection connection = OracleJDBCConnector.getConnection();) {
            CallableStatement stmnt = connection.prepareCall("BEGIN INSERT_SE_ZARIADENIE(?); END;");
            stmnt.setInt(1, object.getTyp());
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<SeZariadenie> findAll() {
        try (Connection connection = OracleJDBCConnector.getConnection();) {
            CallableStatement stmnt = connection.prepareCall("SELECT * FROM SE_ZARIADENIE ORDER BY CIS_ZARIADENIA ASC");
            ResultSet result = stmnt.executeQuery();
            List<SeZariadenie> output = new LinkedList<>();
            while (result.next()) {
                SeZariadenie o = new SeZariadenie();
                o.setTyp(result.getInt("TYP"));
                o.setCisZariadenia(result.getInt("CIS_ZARIADENIA"));
                o.setSpotreba(result.getInt("SPOTREBA"));
                output.add(o);
            }
            return output;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(SeZariadenie object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(SeZariadenie object) {
        try (Connection connection = OracleJDBCConnector.getConnection();) {
            CallableStatement stmnt = connection.prepareCall("BEGIN DELETE_SE_ZARIADENIE(?); END;");
            stmnt.setInt(1, object.getCisZariadenia());
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized SeZariadenieService getInstance() {
        if (instance == null) {
            instance = new SeZariadenieService();
        }
        return instance;
    }

}
