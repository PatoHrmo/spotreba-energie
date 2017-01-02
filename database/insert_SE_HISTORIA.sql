
CREATE OR REPLACE PROCEDURE insert_SE_HISTORIA(
     nove_cislo_odberatela IN SE_HISTORIA.cislo_odberatela%TYPE,
     nove_cislo_zariadenia IN SE_HISTORIA.cis_zariadenia%TYPE,
     novy_datum_instalacie IN SE_HISTORIA.datum_instalacie%TYPE
     )
IS

BEGIN
  
  
  INSERT INTO SE_HISTORIA VALUES (nove_cislo_odberatela, nove_cislo_zariadenia, novy_datum_instalacie, null);

  COMMIT;

END;
/