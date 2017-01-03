/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.uniza.fri.pds.spotreba.energie.service;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Struct;
import java.util.LinkedList;
import java.util.List;
import sk.uniza.fri.pds.spotreba.energie.OracleJDBCConnector;
import sk.uniza.fri.pds.spotreba.energie.domain.SeServis;

public class SeServisService implements SeService<SeServis> {

    private static volatile SeServisService instance;

    private SeServisService() {

    }

    @Override
    public void create(SeServis object) {
        try (Connection connection = OracleJDBCConnector.getConnection();) {
            CallableStatement stmnt = connection.prepareCall("BEGIN INSERT_SE_SERVIS(?, ?, ?, ?, ?); END;");
            stmnt.setDate(1, Utils.utilDateToSqlDate(object.getDatumServisu()));
            stmnt.setInt(2, object.getCisZariadenia());
            stmnt.setString(3, object.getInfoServisu().getPopis());
            stmnt.setString(4, new Character(object.getInfoServisu().getTyp_servisu()).toString());
            stmnt.setInt(5, object.getInfoServisu().getSpotreba());
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<SeServis> findAll() {
        try (Connection connection = OracleJDBCConnector.getConnection();) {
            CallableStatement stmnt = connection.prepareCall("SELECT * FROM SE_SERVIS ORDER BY DATUM_SERVISU ASC");
            ResultSet result = stmnt.executeQuery();
            List<SeServis> output = new LinkedList<>();
            while (result.next()) {
                SeServis o = new SeServis();
                o.setCisZariadenia(result.getInt("CIS_ZARIADENIA"));
                o.setDatumServisu(result.getDate("DATUM_SERVISU"));
                Struct object = (Struct) result.getObject("INFO_SERVISU");
                Object[] attributes = object.getAttributes();
                SeServis.InfoServisu infoServisu = new SeServis.InfoServisu();
                infoServisu.setPopis((String) attributes[0]);
                infoServisu.setTyp_servisu(((String) attributes[1]).charAt(0));
                infoServisu.setSpotreba(((BigDecimal) attributes[2]).intValue());
                o.setInfoServisu(infoServisu);
                output.add(o);
            }
            return output;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(SeServis object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(SeServis object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static synchronized SeServisService getInstance() {
        if (instance == null) {
            instance = new SeServisService();
        }
        return instance;
    }

}
