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
        try (Connection connection = OracleJDBCConnector.getConnection();) {
            CallableStatement stmnt = connection.prepareCall("BEGIN INSERT_SE_ADRESA(?, ?, ?); END;");
            stmnt.setInt(1, object.getIdMesta());
            stmnt.setString(2, object.getCislo());
            stmnt.setString(3, object.getUlica());
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<SeAdresa> findAll() {
        try (Connection connection = OracleJDBCConnector.getConnection();) {
            CallableStatement stmnt = connection.prepareCall("SELECT * FROM SE_ADRESA ORDER BY ID_ADRESY ASC");
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
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(SeAdresa old, SeAdresa object) {
        try (Connection connection = OracleJDBCConnector.getConnection();) {
            CallableStatement stmnt = connection.prepareCall("BEGIN UPDATE_SE_ADRESA(?, ?, ?, ?); END;");
            stmnt.setInt(1, old.getIdAdresy());
            stmnt.setInt(2, object.getIdMesta());
            stmnt.setString(3, object.getCislo());
            stmnt.setString(4, object.getUlica());
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(SeAdresa object) {
        try (Connection connection = OracleJDBCConnector.getConnection();) {
            CallableStatement stmnt = connection.prepareCall("BEGIN DELETE_SE_ADRESA(?); END;");
            stmnt.setInt(1, object.getIdAdresy());
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized SeAdresaService getInstance() {
        if (instance == null) {
            instance = new SeAdresaService();
        }
        return instance;
    }

}
