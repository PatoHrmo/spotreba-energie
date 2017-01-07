/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.uniza.fri.pds.spotreba.energie.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import sk.uniza.fri.pds.spotreba.energie.OracleJDBCConnector;
import sk.uniza.fri.pds.spotreba.energie.domain.SeZamestnanec;
import sk.uniza.fri.pds.spotreba.energie.domain.SeZamestnanecInfo;
import sk.uniza.fri.pds.spotreba.energie.service.util.ZamestnanecRegionParams;

public class SeZamestnanecService implements SeService<SeZamestnanec> {

    private static volatile SeZamestnanecService instance;

    private SeZamestnanecService() {

    }

    @Override
    public void create(SeZamestnanec object) {
        try (Connection connection = OracleJDBCConnector.getConnection();) {
            CallableStatement stmnt = connection.prepareCall("BEGIN INSERT_SE_ZAMESTNANEC(?, ?, ?); END;");
            stmnt.setString(1, object.getRodCislo());
            stmnt.setInt(2, object.getIdRegionu());
            if (object.getFoto() != null) {
                try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
                    ImageIO.write(object.getFoto(), "jpeg", os);
                    InputStream is = new ByteArrayInputStream(os.toByteArray());
                    stmnt.setBlob(3, is);
                } catch (IOException e) {
                    setNullBlob(stmnt);
                    throw e;
                }
            } else {
                setNullBlob(stmnt);
            }

            stmnt.execute();
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setNullBlob(CallableStatement stmnt) throws SQLException {
        InputStream is = null;
        stmnt.setBlob(3, is);
    }

    @Override
    public List<SeZamestnanec> findAll() {
        try (Connection connection = OracleJDBCConnector.getConnection();) {
            CallableStatement stmnt = connection.prepareCall("SELECT * FROM SE_ZAMESTNANEC ORDER BY ID_ZAMESTNANCA ASC");
            ResultSet result = stmnt.executeQuery();
            List<SeZamestnanec> output = new LinkedList<>();
            while (result.next()) {
                SeZamestnanec o = parseOneFromResult(result);
                output.add(o);
            }
            return output;
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<SeZamestnanecInfo> findInRegion(ZamestnanecRegionParams params) {
        try (Connection connection = OracleJDBCConnector.getConnection();) {
            CallableStatement stmnt = connection.prepareCall("select id_zamestnanca, meno, priezvisko from SE_OSOBA join SE_ZAMESTNANEC using(rod_cislo)\n"
                    + "where id_regionu = ?");
            stmnt.setInt(1, params.getIdRegionu());
            ResultSet result = stmnt.executeQuery();
            List<SeZamestnanecInfo> output = new LinkedList<>();
            while (result.next()) {
                SeZamestnanecInfo o = new SeZamestnanecInfo();
                o.setIdZamestnanca(result.getInt("ID_ZAMESTNANCA"));
                o.setMeno(result.getString("meno"));
                o.setPriezvisko(result.getString("PRIEZVISKO"));
                output.add(o);
            }
            return output;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ZamestnanecOdpisReport> findBest(int count) {
        try (Connection connection = OracleJDBCConnector.getConnection();) {
            CallableStatement stmnt = connection.prepareCall("select * from(\n"
                    + "select id_zamestnanca, meno, priezvisko, COUNT(*) pocet_odpisov, rank() over (order by count(*) desc)  pozicia\n"
                    + "from SE_OSOBA join SE_ZAMESTNANEC using(rod_cislo) join SE_ODPIS using (id_zamestnanca)\n"
                    + "where trunc(datum_odpisu,'YEAR') = add_months(trunc(sysdate,'YEAR'),-12)\n"
                    + "group by meno,priezvisko, id_zamestnanca) where pozicia<=? ORDER BY pozicia");
            stmnt.setInt(1, count);
            ResultSet result = stmnt.executeQuery();
            List<ZamestnanecOdpisReport> output = new LinkedList<>();
            while (result.next()) {
                ZamestnanecOdpisReport o = new ZamestnanecOdpisReport();
                o.setIdZamestnanca(result.getInt("ID_ZAMESTNANCA"));
                o.setMeno(result.getString("meno"));
                o.setPriezvisko(result.getString("PRIEZVISKO"));
                o.setPocetOdpisov(result.getInt("POCET_ODPISOV"));
                output.add(o);
            }
            return output;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private SeZamestnanec parseOneFromResult(ResultSet result) throws SQLException, IOException {
        SeZamestnanec o = new SeZamestnanec();
        o.setIdZamestnanca(result.getInt("ID_ZAMESTNANCA"));
        o.setIdRegionu(result.getInt("ID_REGIONU"));
        o.setRodCislo(result.getString("ROD_CISLO"));
        Blob blob = result.getBlob("FOTO");
        if (blob != null) {
            ImageInputStream is = ImageIO.createImageInputStream(blob.getBinaryStream());
            BufferedImage image = ImageIO.read(is);
            o.setFoto(image);

        }
        return o;
    }

    @Override
    public void update(SeZamestnanec old, SeZamestnanec object) {
        try (Connection connection = OracleJDBCConnector.getConnection();) {
            CallableStatement stmnt = connection.prepareCall("BEGIN UPDATE_SE_ZAMESTNANEC(?, ?, ?); END;");
            stmnt.setInt(1, object.getIdZamestnanca());
            stmnt.setInt(2, object.getIdRegionu());
            if (object.getFoto() != null) {
                try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
                    ImageIO.write(object.getFoto(), "jpeg", os);
                    InputStream is = new ByteArrayInputStream(os.toByteArray());
                    stmnt.setBlob(3, is);
                } catch (IOException e) {
                    setNullBlob(stmnt);
                    throw e;
                }
            } else {
                setNullBlob(stmnt);
            }

            stmnt.execute();
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(SeZamestnanec object) {
        try (Connection connection = OracleJDBCConnector.getConnection();) {
            CallableStatement stmnt = connection.prepareCall("BEGIN DELETE_SE_ZAMESTNANEC(?); END;");
            stmnt.setInt(1, object.getIdZamestnanca());
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized SeZamestnanecService getInstance() {
        if (instance == null) {
            instance = new SeZamestnanecService();
        }
        return instance;
    }

}
