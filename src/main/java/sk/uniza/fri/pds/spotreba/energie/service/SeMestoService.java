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
import sk.uniza.fri.pds.spotreba.energie.domain.SeMesto;

public class SeMestoService implements SeService<SeMesto> {

    private static volatile SeMestoService instance;

    private SeMestoService() {

    }

    @Override
    public void create(SeMesto object) {
        try (Connection connection = OracleJDBCConnector.getConnection();) {
            CallableStatement stmnt = connection.prepareCall("BEGIN INSERT_SE_MESTO(?, ?, ?); END;");
            stmnt.setInt(1, object.getIdRegionu());
            stmnt.setString(2, object.getPsc());
            stmnt.setString(3, object.getNazov());
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<SeMesto> findAll() {
        try (Connection connection = OracleJDBCConnector.getConnection();) {
            CallableStatement stmnt = connection.prepareCall("SELECT * FROM SE_MESTO ORDER BY ID_MESTA ASC");
            ResultSet result = stmnt.executeQuery();
            List<SeMesto> output = new LinkedList<>();
            while (result.next()) {
                SeMesto o = new SeMesto();
                o.setIdMesta(result.getInt("ID_MESTA"));
                o.setIdRegionu(result.getInt("ID_REGIONU"));
                o.setNazov(result.getString("NAZOV"));
                o.setPsc(result.getString("PSC"));
                output.add(o);
            }
            return output;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(SeMesto old, SeMesto object) {
        try (Connection connection = OracleJDBCConnector.getConnection();) {
            CallableStatement stmnt = connection.prepareCall("BEGIN UPDATE_SE_MESTO(?, ?, ?, ?); END;");
            stmnt.setInt(1, old.getIdMesta());
            stmnt.setInt(2, object.getIdRegionu());
            stmnt.setString(3, object.getPsc());
            stmnt.setString(4, object.getNazov());
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(SeMesto object) {
        try (Connection connection = OracleJDBCConnector.getConnection();) {
            CallableStatement stmnt = connection.prepareCall("BEGIN DELETE_SE_MESTO(?); END;");
            stmnt.setInt(1, object.getIdMesta());
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized SeMestoService getInstance() {
        if (instance == null) {
            instance = new SeMestoService();
        }
        return instance;
    }

}
