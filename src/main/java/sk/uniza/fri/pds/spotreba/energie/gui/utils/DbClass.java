/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.uniza.fri.pds.spotreba.energie.gui.utils;

import sk.uniza.fri.pds.spotreba.energie.domain.SeAdresa;
import sk.uniza.fri.pds.spotreba.energie.domain.SeFirma;
import sk.uniza.fri.pds.spotreba.energie.domain.SeHistoria;
import sk.uniza.fri.pds.spotreba.energie.domain.SeMesto;
import sk.uniza.fri.pds.spotreba.energie.domain.SeOdberatel;
import sk.uniza.fri.pds.spotreba.energie.domain.SeOdpis;
import sk.uniza.fri.pds.spotreba.energie.domain.SeOsoba;
import sk.uniza.fri.pds.spotreba.energie.domain.SeRegion;
import sk.uniza.fri.pds.spotreba.energie.domain.SeServis;
import sk.uniza.fri.pds.spotreba.energie.domain.SeTypZariadenia;
import sk.uniza.fri.pds.spotreba.energie.domain.SeZamestnanec;
import sk.uniza.fri.pds.spotreba.energie.domain.SeZariadenie;
import sk.uniza.fri.pds.spotreba.energie.service.SeAdresaService;
import sk.uniza.fri.pds.spotreba.energie.service.SeFirmaService;
import sk.uniza.fri.pds.spotreba.energie.service.SeHistoriaService;
import sk.uniza.fri.pds.spotreba.energie.service.SeMestoService;
import sk.uniza.fri.pds.spotreba.energie.service.SeOdberatelService;
import sk.uniza.fri.pds.spotreba.energie.service.SeOdpisService;
import sk.uniza.fri.pds.spotreba.energie.service.SeOsobaService;
import sk.uniza.fri.pds.spotreba.energie.service.SeRegionService;
import sk.uniza.fri.pds.spotreba.energie.service.SeService;
import sk.uniza.fri.pds.spotreba.energie.service.SeServisService;
import sk.uniza.fri.pds.spotreba.energie.service.SeTypZariadeniaService;
import sk.uniza.fri.pds.spotreba.energie.service.SeZamestnanecService;
import sk.uniza.fri.pds.spotreba.energie.service.SeZariadenieService;

public enum DbClass {
    Adresa(SeAdresa.class, SeAdresaService.getInstance()),
    Firma(SeFirma.class, SeFirmaService.getInstance()),
    Historia(SeHistoria.class, SeHistoriaService.getInstance()),
    Mesto(SeMesto.class, SeMestoService.getInstance()),
    Odberatel(SeOdberatel.class, SeOdberatelService.getInstance()),
    Odpis(SeOdpis.class, SeOdpisService.getInstance()),
    Osoba(SeOsoba.class, SeOsobaService.getInstance()),
    Region(SeRegion.class, SeRegionService.getInstance()),
    Servis(SeServis.class, SeServisService.getInstance()),
    TypZariadenia(SeTypZariadenia.class, SeTypZariadeniaService.getInstance()),
    Zamestnanec(SeZamestnanec.class, SeZamestnanecService.getInstance()),
    Zariadenie(SeZariadenie.class, SeZariadenieService.getInstance());

    public final Class clazz;
    public final SeService service;

    private DbClass(Class clazz, SeService service) {
        this.clazz = clazz;
        this.service = service;
    }

}
