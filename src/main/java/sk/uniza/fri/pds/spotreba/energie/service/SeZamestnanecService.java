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

public class SeZamestnanecService implements SeService<SeZamestnanec> {

    private static volatile SeZamestnanecService instance;

    private SeZamestnanecService() {

    }

    @Override
    public void create(SeZamestnanec object) {
        try (Connection connection = OracleJDBCConnector.getConnection();) {
            CallableStatement stmnt = connection.prepareCall("BEGIN INSERT_SE_ZAMESTNANEC(?, ?, ?); END;");
            stmnt.setString(1, object.getRodCislo());
            stmnt.setInt(1, object.getIdRegionu());
            try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
                ImageIO.write(object.getFoto(), "jpeg", os);
                InputStream is = new ByteArrayInputStream(os.toByteArray());
                stmnt.setBlob(3, is);
            }
            stmnt.execute();
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<SeZamestnanec> findAll() {
        try (Connection connection = OracleJDBCConnector.getConnection();) {
            CallableStatement stmnt = connection.prepareCall("SELECT * FROM SE_ZAMESTNANEC ORDER BY ID_ZAMESTNANCA ASC");
            ResultSet result = stmnt.executeQuery();
            List<SeZamestnanec> output = new LinkedList<>();
            while (result.next()) {
                SeZamestnanec o = new SeZamestnanec();
                o.setIdZamestnanca(result.getInt("ID_ZAMESTNANCA"));
                o.setIdRegionu(result.getInt("ID_REGIONU"));
                o.setRodCislo(result.getString("ROD_CISLO"));
                Blob blob = result.getBlob("FOTO");
                if (blob != null) {
                    try (ImageInputStream is = ImageIO.createImageInputStream(blob.getBinaryStream())) {
                        BufferedImage image = ImageIO.read(is);
                        o.setFoto(image);
                    }
                }
                output.add(o);
            }
            return output;
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(SeZamestnanec object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(SeZamestnanec object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static synchronized SeZamestnanecService getInstance() {
        if (instance == null) {
            instance = new SeZamestnanecService();
        }
        return instance;
    }

}
