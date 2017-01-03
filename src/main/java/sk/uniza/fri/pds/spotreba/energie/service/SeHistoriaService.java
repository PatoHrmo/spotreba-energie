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
import sk.uniza.fri.pds.spotreba.energie.domain.SeHistoria;

public class SeHistoriaService implements SeService<SeHistoria> {

    private static volatile SeHistoriaService instance;

    private SeHistoriaService() {

    }

    @Override
    public void create(SeHistoria object) {
        try (Connection connection = OracleJDBCConnector.getConnection();) {
            CallableStatement stmnt = connection.prepareCall("BEGIN INSERT_SE_HISTORIA(?, ?, ?); END;");
            stmnt.setInt(1, object.getCisloOdberatela());
            stmnt.setInt(2, object.getCisZariadenia());
            stmnt.setDate(3, Utils.utilDateToSqlDate(object.getDatumInstalacie()));
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<SeHistoria> findAll() {
        try (Connection connection = OracleJDBCConnector.getConnection();) {
            CallableStatement stmnt = connection.prepareCall("SELECT * FROM SE_HISTORIA ORDER BY CISLO_ODBERATELA ASC, CIS_ZARIADENIA");
            ResultSet result = stmnt.executeQuery();
            List<SeHistoria> output = new LinkedList<>();
            while (result.next()) {
                SeHistoria o = new SeHistoria();
                o.setCisZariadenia(result.getInt("CIS_ZARIADENIA"));
                o.setCisloOdberatela(result.getInt("CISLO_ODBERATELA"));
                o.setDatumInstalacie(result.getDate("DATUM_INSTALACIE"));
                o.setDatumOdobratia(result.getDate("DATUM_ODOBRATIA"));
                output.add(o);
            }
            return output;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(SeHistoria object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(SeHistoria object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static synchronized SeHistoriaService getInstance() {
        if (instance == null) {
            instance = new SeHistoriaService();
        }
        return instance;
    }

}
