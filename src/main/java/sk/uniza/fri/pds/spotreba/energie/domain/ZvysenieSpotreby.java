package sk.uniza.fri.pds.spotreba.energie.domain;

import sk.uniza.fri.pds.spotreba.energie.domain.util.MeraciaVelicina;
import sk.uniza.fri.pds.spotreba.energie.gui.utils.DisplayPriority;
import sk.uniza.fri.pds.spotreba.energie.gui.utils.TableCollumnName;

public class ZvysenieSpotreby {

    private String meno;
    private MeraciaVelicina velicina;
    private double priemernaSpotrebaVMinulosti;
    private double zvysenaSpotreba;

    @DisplayPriority(priority = 1)
    public String getMeno() {
        return meno;
    }

    public void setMeno(String meno) {
        this.meno = meno;
    }

    @DisplayPriority(priority = 2)
    public MeraciaVelicina getVelicina() {
        return velicina;
    }

    public void setVelicina(MeraciaVelicina velicina) {
        this.velicina = velicina;
    }

    @DisplayPriority(priority = 3)
    @TableCollumnName(name = "Priemerná spotreba v minulosti (za deň)")
    public double getPriemernaSpotrebaVMinulosti() {
        return priemernaSpotrebaVMinulosti;
    }

    public void setPriemernaSpotrebaVMinulosti(double priemernaSpotrebaVMinulosti) {
        this.priemernaSpotrebaVMinulosti = priemernaSpotrebaVMinulosti;
    }

    @TableCollumnName(name = "Priemerná spotreba v sledovanom období (za deň)")
    public double getZvysenaSpotreba() {
        return zvysenaSpotreba;
    }

    public void setZvysenaSpotreba(double zvysenaSpotreba) {
        this.zvysenaSpotreba = zvysenaSpotreba;
    }

}
