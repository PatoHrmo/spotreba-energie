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
import sk.uniza.fri.pds.spotreba.energie.domain.SeTypZariadenia;

public class SeTypZariadeniaService implements SeService<SeTypZariadenia> {

    private static volatile SeTypZariadeniaService instance;

    private SeTypZariadeniaService() {

    }

    @Override
    public void create(SeTypZariadenia object) {
        try (Connection connection = OracleJDBCConnector.getConnection();) {
            CallableStatement stmnt = connection.prepareCall("BEGIN INSERT_SE_TYP_ZARIADENIA(?, ?, ?); END;");
            stmnt.setString(1, object.getMeraciaVelicina());
            stmnt.setInt(2, object.getCisloModelu());
            stmnt.setString(3, object.getVyrobca());
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<SeTypZariadenia> findAll() {
        try (Connection connection = OracleJDBCConnector.getConnection();) {
            CallableStatement stmnt = connection.prepareCall("SELECT * FROM SE_TYP_ZARIADENIA ORDER BY TYP ASC");
            ResultSet result = stmnt.executeQuery();
            List<SeTypZariadenia> output = new LinkedList<>();
            while (result.next()) {
                SeTypZariadenia o = new SeTypZariadenia();
                o.setTyp(result.getInt("TYP"));
                o.setMeraciaVelicina(result.getString("MERACIA_VELICINA"));
                o.setCisloModelu(result.getInt("CISLO_MODELU"));
                o.setVyrobca(result.getString("VYROBCA"));
                output.add(o);
            }
            return output;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(SeTypZariadenia object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(SeTypZariadenia object) {
        try (Connection connection = OracleJDBCConnector.getConnection();) {
            CallableStatement stmnt = connection.prepareCall("BEGIN DELETE_SE_TYP_ZARIADENIA(?); END;");
            stmnt.setInt(1, object.getTyp());
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized SeTypZariadeniaService getInstance() {
        if (instance == null) {
            instance = new SeTypZariadeniaService();
        }
        return instance;
    }

}
