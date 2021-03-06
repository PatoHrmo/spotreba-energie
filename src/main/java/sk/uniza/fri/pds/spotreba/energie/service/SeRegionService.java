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
import sk.uniza.fri.pds.spotreba.energie.domain.SeRegion;

public class SeRegionService implements SeService<SeRegion> {

    private static volatile SeRegionService instance;

    private SeRegionService() {

    }

    @Override
    public void create(SeRegion object) {
        try (Connection connection = OracleJDBCConnector.getConnection();) {
            CallableStatement stmnt = connection.prepareCall("BEGIN INSERT_SE_REGION(?); END;");
            stmnt.setString(1, object.getNazov());
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<SeRegion> findAll() {
        try (Connection connection = OracleJDBCConnector.getConnection();) {
            CallableStatement stmnt = connection.prepareCall("SELECT * FROM SE_REGION ORDER BY ID_REGIONU ASC");
            ResultSet result = stmnt.executeQuery();
            List<SeRegion> output = new LinkedList<>();
            while (result.next()) {
                SeRegion o = new SeRegion();
                o.setIdRegionu(result.getInt("ID_REGIONU"));
                o.setNazov(result.getString("NAZOV"));
                output.add(o);
            }
            return output;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(SeRegion old, SeRegion object) {
        try (Connection connection = OracleJDBCConnector.getConnection();) {
            CallableStatement stmnt = connection.prepareCall("BEGIN UPDATE_SE_REGION(?, ?); END;");
            stmnt.setInt(1, old.getIdRegionu());
            stmnt.setString(2, object.getNazov());
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(SeRegion object) {
        try (Connection connection = OracleJDBCConnector.getConnection();) {
            CallableStatement stmnt = connection.prepareCall("BEGIN DELETE_SE_REGION(?); END;");
            stmnt.setInt(1, object.getIdRegionu());
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized SeRegionService getInstance() {
        if (instance == null) {
            instance = new SeRegionService();
        }
        return instance;
    }

}
