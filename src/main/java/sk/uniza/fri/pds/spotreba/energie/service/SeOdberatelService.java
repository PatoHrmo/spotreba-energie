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
import sk.uniza.fri.pds.spotreba.energie.domain.SeOdberatel;

public class SeOdberatelService implements SeService<SeOdberatel> {

    private static volatile SeOdberatelService instance;

    private SeOdberatelService() {

    }

    @Override
    public void create(SeOdberatel object) {
        try (Connection connection = OracleJDBCConnector.getConnection();) {
            CallableStatement stmnt = connection.prepareCall("BEGIN INSERT_SE_ODBERATEL(?, ?, ?, ?); END;");
            stmnt.setString(1, object.getIco());
            stmnt.setString(2, object.getRodCislo());
            stmnt.setString(3, object.getTyp() == null ? null : object.getTyp().toString());
            stmnt.setString(4, object.getKategoria() == null ? null : object.getKategoria().toString());
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<SeOdberatel> findAll() {
        try (Connection connection = OracleJDBCConnector.getConnection();) {
            CallableStatement stmnt = connection.prepareCall("SELECT * FROM SE_ODBERATEL ORDER BY CISLO_ODBERATELA ASC");
            ResultSet result = stmnt.executeQuery();
            List<SeOdberatel> output = new LinkedList<>();
            while (result.next()) {
                SeOdberatel o = new SeOdberatel();
                o.setCisloOdberatela(result.getInt("CISLO_ODBERATELA"));
                o.setIco(result.getString("ICO"));
                o.setKategoria(result.getString("KATEGORIA").charAt(0));
                o.setRodCislo(result.getString("ROD_CISLO"));
                o.setTyp(result.getString("TYP").charAt(0));
                output.add(o);
            }
            return output;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(SeOdberatel object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(SeOdberatel object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static synchronized SeOdberatelService getInstance() {
        if (instance == null) {
            instance = new SeOdberatelService();
        }
        return instance;
    }

}
