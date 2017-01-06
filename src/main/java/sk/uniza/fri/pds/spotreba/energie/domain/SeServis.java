package sk.uniza.fri.pds.spotreba.energie.domain;

import java.io.Serializable;
import java.util.Date;

public class SeServis implements Serializable {

    private static final long serialVersionUID = 1L;
    private int cisZariadenia;
    private Date datumServisu;
    private InfoServisu infoServisu;

    public SeServis() {
        infoServisu = new InfoServisu();
    }

    public static class InfoServisu implements Serializable {

        private static final long serialVersionUID = 2L;
        private String popis;
        private TypServisu typ_servisu;
        private int spotreba;

        public String getPopis() {
            return popis;
        }

        public void setPopis(String popis) {
            this.popis = popis;
        }

        public TypServisu getTyp_servisu() {
            return typ_servisu;
        }

        public void setTyp_servisu(TypServisu typ_servisu) {
            this.typ_servisu = typ_servisu;
        }

        public int getSpotreba() {
            return spotreba;
        }

        public void setSpotreba(int spotreba) {
            this.spotreba = spotreba;
        }

        @Override
        public String toString() {
            return String.format("Popis: %s %nTyp: %s %nSpotreba: %d", popis, typ_servisu, spotreba);
        }

    }

    public int getCisZariadenia() {
        return cisZariadenia;
    }

    public void setCisZariadenia(int cisZariadenia) {
        this.cisZariadenia = cisZariadenia;
    }

    public Date getDatumServisu() {
        return datumServisu;
    }

    public void setDatumServisu(Date datumServisu) {
        this.datumServisu = datumServisu;
    }

    public InfoServisu getInfoServisu() {
        return infoServisu;
    }

    public void setInfoServisu(InfoServisu infoServisu) {
        this.infoServisu = infoServisu;
    }

    public enum TypServisu {
        DIAGNOSTIKA('0'), OPRAVA('1'), NASTAVENIE('2');

        public final Character val;

        private TypServisu(char val) {
            this.val = val;
        }

        public static TypServisu getByValue(char val) {
            if (val == '0') {
                return DIAGNOSTIKA;
            }
            if (val == '1') {
                return OPRAVA;
            }
            return NASTAVENIE;
        }

    }

}
