CREATE OR REPLACE PROCEDURE insert_SE_ADRESA(
	   id_mesta IN SE_adresa.id_mesta%TYPE,
     cislo IN SE_adresa.cislo%TYPE,
     ulica IN SE_adresa.ulica%TYPE
     )
IS
BEGIN

  INSERT INTO SE_adresa VALUES (seq_id_adresy.nextval, id_mesta, cislo, ulica);

  COMMIT;

END;
/