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
import sk.uniza.fri.pds.spotreba.energie.domain.KrokSpotreby;
import sk.uniza.fri.pds.spotreba.energie.domain.SeHistoria;
import sk.uniza.fri.pds.spotreba.energie.domain.ZvysenieSpotreby;
import sk.uniza.fri.pds.spotreba.energie.domain.util.MeraciaVelicina;
import sk.uniza.fri.pds.spotreba.energie.service.util.IncreasedSpendingStatisticParams;
import sk.uniza.fri.pds.spotreba.energie.service.util.SpendingStatisticsParameters;

public class SeHistoriaService implements SeService<SeHistoria> {

    private static volatile SeHistoriaService instance;

    private SeHistoriaService() {

    }

    @Override
    public void create(SeHistoria object) {
        try (Connection connection = OracleJDBCConnector.getConnection();) {
            CallableStatement stmnt = connection.prepareCall("BEGIN INSERT_SE_HISTORIA(?, ?, ?,?); END;");
            stmnt.setInt(1, object.getCisloOdberatela());
            stmnt.setInt(2, object.getCisZariadenia());
            stmnt.setDate(3, Utils.utilDateToSqlDate(object.getDatumInstalacie()));
            stmnt.setInt(4, object.getZamestnanecVykonvajuciZmenu());
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
    public void update(SeHistoria old, SeHistoria object) {
        try (Connection connection = OracleJDBCConnector.getConnection();) {
            CallableStatement stmnt = connection.prepareCall("BEGIN ODOBER_ZARIADENIE(?, ?, ?, ?, ?); END;");
            stmnt.setInt(1, old.getCisloOdberatela());
            stmnt.setInt(2, old.getCisZariadenia());
            stmnt.setDate(3, Utils.utilDateToSqlDate(object.getDatumOdobratia()));
            stmnt.setInt(4, object.getZamestnanecVykonvajuciZmenu());
            stmnt.setInt(5, object.getSpotrebaPredOdobratim());
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(SeHistoria object) {
        throw new RuntimeException("Pre túto tabuľku bola táto funkcionalita zablokovaná!");
    }

    public List<KrokSpotreby> getSpendingStatistics(SpendingStatisticsParameters params) {
        try (Connection connection = OracleJDBCConnector.getConnection();) {
            CallableStatement stmnt = connection.prepareCall("SELECT * FROM TABLE(get_statistika_spotreby(?,?,?,?,?))");
            stmnt.setInt(1, params.getIdSpotrebitela());
            stmnt.setDate(2, Utils.utilDateToSqlDate(params.getDatumOd()));
            stmnt.setDate(3, Utils.utilDateToSqlDate(params.getDatumDo()));
            stmnt.setInt(4, params.getGranularita().val);
            stmnt.setString(5, params.getVelicina().name().toLowerCase());
            ResultSet result = stmnt.executeQuery();
            List<KrokSpotreby> output = new LinkedList<>();
            while (result.next()) {
                KrokSpotreby o = new KrokSpotreby();
                o.setDatumOd(result.getDate("DATUM_OD"));
                o.setDatumDo(result.getDate("DATUM_DO"));
                o.setSpotreba(result.getDouble("SPOTREBA"));
                output.add(o);
            }
            return output;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ZvysenieSpotreby> getIncreasedSpendingStatistics(IncreasedSpendingStatisticParams params) {
        try (Connection connection = OracleJDBCConnector.getConnection();) {
            CallableStatement stmnt = connection.prepareCall("SELECT * FROM TABLE(get_zvysena_miera_spotreby(?,?))");
            stmnt.setDate(1, Utils.utilDateToSqlDate(params.getDatumOd()));
            stmnt.setDate(2, Utils.utilDateToSqlDate(params.getDatumDo()));
            ResultSet result = stmnt.executeQuery();
            List<ZvysenieSpotreby> output = new LinkedList<>();
            while (result.next()) {
                ZvysenieSpotreby o = new ZvysenieSpotreby();
                o.setMeno(result.getString("MENO"));
                o.setPriemernaSpotrebaVMinulosti(result.getDouble("PRIEMERNA_SPOTREBA_V_MINULOSTI"));
                o.setVelicina(MeraciaVelicina.valueOf(result.getString("VELICINA").toUpperCase()));
                o.setZvysenaSpotreba(result.getDouble("ZVYSENA_SPOTREBA"));
                output.add(o);
            }
            return output;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized SeHistoriaService getInstance() {
        if (instance == null) {
            instance = new SeHistoriaService();
        }
        return instance;
    }

}
