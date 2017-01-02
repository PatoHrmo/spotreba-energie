CREATE OR REPLACE PROCEDURE insert_SE_FIRMA(
	   ico IN SE_FIRMA.ico%TYPE,
     id_adresy IN SE_FIRMA.id_adresy%TYPE,
     nazov_firmy IN SE_FIRMA.nazov_firmy%TYPE
     )
IS
BEGIN

  INSERT INTO SE_FIRMA VALUES (ico, id_adresy, nazov_firmy);

  COMMIT;

END;
/