CREATE OR REPLACE PROCEDURE update_SE_SERVIS(
     datum_meneneho_servisu IN SE_SERVIS.datum_servisu%TYPE,
     cislo_zariadenia IN SE_SERVIS.cis_zariadenia%TYPE,
     novy_popis IN SE_SERVIS.INFO_SERVISU.popis%TYPE,
     novy_typ_servisu IN SE_SERVIS.INFO_SERVISU.typ_servisu%TYPE,
     nova_namerana_spotreba IN SE_SERVIS.INFO_SERVISU.spotreba%TYPE
     )
IS
posledne_namerana_spotreba SE_ZARIADENIE.spotreba%TYPE;
spotreba_nemoze_klesat EXCEPTION;
BEGIN
  select spotreba into posledne_namerana_spotreba from SE_ZARIADENIE where cis_zariadenia = cislo_zariadenia;
  
  IF posledne_namerana_spotreba >  nova_namerana_spotreba THEN
  RAISE spotreba_nemoze_klesat;
  END IF;

  UPDATE SE_SERVIS
  SET info_servisu = t_info_servisu(novy_popis, novy_typ_servisu, nova_namerana_spotreba)
  where datum_servisu = datum_meneneho_servisu and cis_zariadenia = cislo_zariadenia;
  
  COMMIT;
  
  EXCEPTION
  WHEN spotreba_nemoze_klesat THEN
    RAISE_APPLICATION_ERROR(-20001,'spotreba od minuleho merania klesla');
END;
/