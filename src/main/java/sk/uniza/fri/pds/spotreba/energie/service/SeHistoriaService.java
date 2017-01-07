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
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import sk.uniza.fri.pds.spotreba.energie.OracleJDBCConnector;
import sk.uniza.fri.pds.spotreba.energie.domain.CelkovaStatistika;
import sk.uniza.fri.pds.spotreba.energie.domain.KrokSpotreby;
import sk.uniza.fri.pds.spotreba.energie.domain.SeHistoria;
import sk.uniza.fri.pds.spotreba.energie.domain.SpotrebaDomacnosti;
import sk.uniza.fri.pds.spotreba.energie.domain.StatistikaTypuKategorie;
import sk.uniza.fri.pds.spotreba.energie.domain.ZvysenieSpotreby;
import sk.uniza.fri.pds.spotreba.energie.domain.util.MeraciaVelicina;
import sk.uniza.fri.pds.spotreba.energie.service.util.IncreasedSpendingStatisticParams;
import sk.uniza.fri.pds.spotreba.energie.service.util.NajminajucejsiSpotrebiteliaParams;
import sk.uniza.fri.pds.spotreba.energie.service.util.SpendingStatisticsParameters;
import sk.uniza.fri.pds.spotreba.energie.service.util.StatistikaSpotriebParams;
import sk.uniza.fri.pds.spotreba.energie.service.util.StatistikaTypuKategorieParams;

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

    public List<ZvysenieSpotreby> getIncreasedSpendingStatistics(IncreasedSpendingStatisticParams params, double loadFactor) {
        try (Connection connection = OracleJDBCConnector.getConnection();) {
            CallableStatement stmnt = connection.prepareCall("SELECT * FROM TABLE(get_zvysena_miera_spotreby(?,?,?))");
            stmnt.setDate(1, Utils.utilDateToSqlDate(params.getDatumOd()));
            stmnt.setDate(2, Utils.utilDateToSqlDate(params.getDatumDo()));
            stmnt.setDouble(3, loadFactor);
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

    public List<StatistikaTypuKategorie> getTypeAndCategoryStatistics(StatistikaTypuKategorieParams params) {
        try (Connection connection = OracleJDBCConnector.getConnection();) {
            CallableStatement stmnt = connection.prepareCall("SELECT * FROM TABLE(get_statistika_typ_a_kategoria(?,?))");
            stmnt.setInt(1, params.getRok());
            stmnt.setString(2, params.getVelicina().name().toLowerCase());
            ResultSet result = stmnt.executeQuery();
            List<StatistikaTypuKategorie> output = new LinkedList<>();
            while (result.next()) {
                StatistikaTypuKategorie o = new StatistikaTypuKategorie();
                o.setTyp(result.getString("TYP_ODBERATELA"));
                o.setKategoria(result.getString("KATEGORIA"));
                o.setMinimalnaSpotreba(result.getDouble("MIN_SPOTREBA"));
                o.setMesiacMinimalnejSpotreby(result.getInt("MESIAC_MIN_SPOTREBY"));
                o.setMaximalnaSpotreba(result.getDouble("MAX_SPOTREBA"));
                o.setMesiacMaximalnejSpotreby(result.getInt("MESIAC_MAX_SPOTREBY"));
                o.setPriemernaSpotreba(result.getDouble("PRIEMER"));
                output.add(o);
            }
            return output;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<CelkovaStatistika> getOveralStatistics(StatistikaSpotriebParams params) {
        try (Connection connection = OracleJDBCConnector.getConnection();) {
            CallableStatement stmnt = connection.prepareCall("SELECT get_najm_najve_spotreba(?,?,?,?,?,?) from dual");
            stmnt.setString(1, params.getTypOdberatela().val.toString());
            stmnt.setString(2, params.getKategoriaOdberatela().name());
            stmnt.setInt(3, params.getIdRegionu());
            stmnt.setString(4, params.getVelicina().name().toLowerCase());
            stmnt.setDate(5, Utils.utilDateToSqlDate(params.getDatumOd()));
            stmnt.setDate(6, Utils.utilDateToSqlDate(params.getDatumDo()));
            ResultSet result = stmnt.executeQuery();
            List<CelkovaStatistika> output = new LinkedList<>();
            while (result.next()) {
                CelkovaStatistika o = new CelkovaStatistika();
                Object[] attributes = ((Struct) result.getObject(1)).getAttributes();
                o.setMesiacMinimalnejSpotreby(((Timestamp) attributes[0]));
                o.setMinimalnaSpotreba(((BigDecimal) attributes[1]).intValue());
                o.setMesiacMaximalnejSpotreby(((Timestamp) attributes[2]));
                o.setMaximalnaSpotreba(((BigDecimal) attributes[3]).intValue());
                output.add(o);
            }
            return output;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<NajminajucejsiSpotrebitel> getNajnminajucejsiSpotrebitelia(NajminajucejsiSpotrebiteliaParams params) {
        try (Connection connection = OracleJDBCConnector.getConnection();) {
            CallableStatement stmnt = connection.prepareCall("select meno, cislo_odberatela from (select  rank() over (\n"
                    + "  order by get_spotreba_za_obdobie(cislo_odberatela,?,?,?)) as rn,\n"
                    + "  count(*) over() as pocet,\n"
                    + "  meno||' '||priezvisko as meno,\n"
                    + "  cislo_odberatela \n"
                    + "  from SE_ODBERATEL join SE_OSOBA using(rod_cislo)) \n"
                    + "where rn<pocet*0.1");
            stmnt.setString(3, params.getVelicina().name().toLowerCase());
            stmnt.setDate(1, Utils.utilDateToSqlDate(params.getDatumOd()));
            stmnt.setDate(2, Utils.utilDateToSqlDate(params.getDatumDo()));
            ResultSet result = stmnt.executeQuery();
            List<NajminajucejsiSpotrebitel> output = new LinkedList<>();
            while (result.next()) {
                NajminajucejsiSpotrebitel o = new NajminajucejsiSpotrebitel();
                o.setMeno(result.getString("meno"));
                o.setCisloOdberatela(result.getInt("cislo_odberatela"));
                output.add(o);
            }
            return output;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<SpotrebaDomacnosti> getProblematickeDOmacnosti() {
        try (Connection connection = OracleJDBCConnector.getConnection();) {
            CallableStatement stmnt = connection.prepareCall("select cislo_odberatela, meno||' '||priezvisko meno, meracia_velicina, get_spotreba_za_obdobie(cislo_odberatela,ADD_MONTHS(sysdate,-12),sysdate,meracia_velicina) spotreba, get_pocet_vymen_zariadenia(cislo_odberatela,ADD_MONTHS(sysdate,-12),sysdate,zariadenie.MERACIA_VELICINA) as pocet_vymen\n"
                    + "from SE_OSOBA join SE_ODBERATEL odberatel on(odberatel.rod_cislo = SE_OSOBA.ROD_CISLO) join SE_HISTORIA using (cislo_odberatela) \n"
                    + "join SE_ZARIADENIE using(cis_zariadenia) join SE_TYP_ZARIADENIA zariadenie on(zariadenie.typ = SE_ZARIADENIE.TYP)\n"
                    + "where get_pocet_vymen_zariadenia(cislo_odberatela,ADD_MONTHS(sysdate,-12),sysdate,zariadenie.MERACIA_VELICINA)>2\n"
                    + "group by cislo_odberatela, SE_OSOBA.rod_cislo, meno, priezvisko,meracia_velicina, get_spotreba_za_obdobie(cislo_odberatela,ADD_MONTHS(sysdate,-12),sysdate,meracia_velicina)");
            ResultSet result = stmnt.executeQuery();
            List<SpotrebaDomacnosti> output = new LinkedList<>();
            while (result.next()) {
                SpotrebaDomacnosti o = new SpotrebaDomacnosti();
                o.setMeno(result.getString("meno"));
                o.setCisloOdberatela(result.getInt("cislo_odberatela"));
                o.setVelicina(MeraciaVelicina.valueOf(result.getString("MERACIA_VELICINA").toUpperCase()));
                o.setSpotreba(result.getDouble("SPOTREBA"));
                o.setPocetVymen(result.getInt("POCET_VYMEN"));
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
