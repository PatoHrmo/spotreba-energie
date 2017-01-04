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
import sk.uniza.fri.pds.spotreba.energie.domain.SeFirma;

public class SeFirmaService implements SeService<SeFirma> {

    private static volatile SeFirmaService instance;

    private SeFirmaService() {

    }

    @Override
    public void create(SeFirma object) {
        try (Connection connection = OracleJDBCConnector.getConnection();) {
            CallableStatement stmnt = connection.prepareCall("BEGIN INSERT_SE_FIRMA(?, ?, ?); END;");
            stmnt.setString(1, object.getIco());
            stmnt.setInt(2, object.getIdAdresy());
            stmnt.setString(3, object.getNazovFirmy());
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<SeFirma> findAll() {
        try (Connection connection = OracleJDBCConnector.getConnection();) {
            CallableStatement stmnt = connection.prepareCall("SELECT * FROM SE_FIRMA ORDER BY ICO ASC");
            ResultSet result = stmnt.executeQuery();
            List<SeFirma> output = new LinkedList<>();
            while (result.next()) {
                SeFirma o = new SeFirma();
                o.setIco(result.getString("ICO"));
                o.setNazovFirmy(result.getString("NAZOV_FIRMY"));
                o.setIdAdresy(result.getInt("ID_ADRESY"));
                output.add(o);
            }
            return output;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(SeFirma old, SeFirma object) {
        try (Connection connection = OracleJDBCConnector.getConnection();) {
            CallableStatement stmnt = connection.prepareCall("BEGIN UPDATE_SE_FIRMA(?, ?, ?, ?); END;");
            stmnt.setString(1, old.getIco());
            stmnt.setString(2, object.getIco());
            stmnt.setInt(3, object.getIdAdresy());
            stmnt.setString(4, object.getNazovFirmy());
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(SeFirma object) {
        try (Connection connection = OracleJDBCConnector.getConnection();) {
            CallableStatement stmnt = connection.prepareCall("BEGIN DELETE_SE_FIRMA(?); END;");
            stmnt.setString(1, object.getIco());
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized SeFirmaService getInstance() {
        if (instance == null) {
            instance = new SeFirmaService();
        }
        return instance;
    }

}
