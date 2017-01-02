
CREATE OR REPLACE PROCEDURE insert_SE_SERVIS(
     datum_servisu IN SE_SERVIS.datum_servisu%TYPE,
     cislo_zariadenia IN SE_SERVIS.cis_zariadenia%TYPE,
     popis IN SE_SERVIS.INFO_SERVISU.popis%TYPE,
     typ_servisu IN SE_SERVIS.INFO_SERVISU.typ_servisu%TYPE,
     namerana_spotreba IN SE_SERVIS.INFO_SERVISU.spotreba%TYPE
     )
IS
posledne_namerana_spotreba SE_ZARIADENIE.spotreba%TYPE;
spotreba_nemoze_klesat EXCEPTION;
BEGIN
  select spotreba into posledne_namerana_spotreba from SE_ZARIADENIE where cis_zariadenia = cislo_zariadenia;
  IF posledne_namerana_spotreba >  namerana_spotreba THEN
  RAISE spotreba_nemoze_klesat;
  END IF;
  INSERT INTO SE_SERVIS VALUES (datum_servisu, cislo_zariadenia, t_info_servisu(popis, typ_servisu, namerana_spotreba));

  COMMIT;

END;
/