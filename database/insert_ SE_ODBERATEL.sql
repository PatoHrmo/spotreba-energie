
CREATE OR REPLACE PROCEDURE insert_SE_ODBERATEL(
	   ico IN SE_ODBERATEL.ico%TYPE,
     rod_cislo IN SE_ODBERATEL.rod_cislo%TYPE,
     typ IN SE_ODBERATEL.typ%TYPE,
     kategoria IN SE_ODBERATEL.kategoria%TYPE
     )
IS
BEGIN

  INSERT INTO SE_ODBERATEL VALUES (seq_cislo_odberatela.nextval, ico, rod_cislo, typ, kategoria);

  COMMIT;

END;
/