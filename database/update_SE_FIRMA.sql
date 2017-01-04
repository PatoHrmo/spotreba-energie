CREATE OR REPLACE TRIGGER uprava_ica_odberatel
BEFORE UPDATE
   ON SE_FIRMA
   FOR EACH ROW

DECLARE
BEGIN
  update SE_ODBERATEL
  SET ico = :new.ico
  WHERE ico = :old.ico;
END;
/

CREATE OR REPLACE PROCEDURE update_SE_FIRMA(
     ico_menenej_firmy SE_FIRMA.ico%TYPE,
	   nove_ico IN SE_FIRMA.ico%TYPE,
     nove_id_adresy IN SE_FIRMA.id_adresy%TYPE,
     novy_nazov_firmy IN SE_FIRMA.nazov_firmy%TYPE
     )
IS
BEGIN

  UPDATE SE_FIRMA
  SET ico = nove_ico,
      id_adresy = nove_id_adresy,
      nazov_firmy = novy_nazov_firmy
  where ico = ico_menenej_firmy;
  COMMIT;

END;
/