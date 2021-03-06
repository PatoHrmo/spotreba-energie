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
import sk.uniza.fri.pds.spotreba.energie.domain.SeOsoba;

public class SeOsobaService implements SeService<SeOsoba> {

    private static volatile SeOsobaService instance;

    private SeOsobaService() {

    }

    @Override
    public void create(SeOsoba object) {
        try (Connection connection = OracleJDBCConnector.getConnection();) {
            CallableStatement stmnt = connection.prepareCall("BEGIN INSERT_SE_OSOBA(?, ?, ?, ?); END;");
            stmnt.setString(1, object.getRodCislo());
            stmnt.setInt(2, object.getIdAdresy());
            stmnt.setString(3, object.getMeno());
            stmnt.setString(4, object.getPriezvisko());
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<SeOsoba> findAll() {
        try (Connection connection = OracleJDBCConnector.getConnection();) {
            CallableStatement stmnt = connection.prepareCall("SELECT * FROM SE_OSOBA ORDER BY ROD_CISLO ASC");
            ResultSet result = stmnt.executeQuery();
            List<SeOsoba> output = new LinkedList<>();
            while (result.next()) {
                SeOsoba o = new SeOsoba();
                o.setRodCislo(result.getString("ROD_CISLO"));
                o.setIdAdresy(result.getInt("ID_ADRESY"));
                o.setMeno(result.getString("MENO"));
                o.setPriezvisko(result.getString("PRIEZVISKO"));
                output.add(o);
            }
            return output;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(SeOsoba old, SeOsoba object) {
        try (Connection connection = OracleJDBCConnector.getConnection();) {
            CallableStatement stmnt = connection.prepareCall("BEGIN UPDATE_SE_OSOBA(?, ?, ?, ?, ?); END;");
            stmnt.setString(1, old.getRodCislo());
            stmnt.setString(2, object.getRodCislo());
            stmnt.setInt(3, object.getIdAdresy());
            stmnt.setString(4, object.getMeno());
            stmnt.setString(5, object.getPriezvisko());
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(SeOsoba object) {
        try (Connection connection = OracleJDBCConnector.getConnection();) {
            CallableStatement stmnt = connection.prepareCall("BEGIN DELETE_SE_OSOBA(?); END;");
            stmnt.setString(1, object.getRodCislo());
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized SeOsobaService getInstance() {
        if (instance == null) {
            instance = new SeOsobaService();
        }
        return instance;
    }

}
