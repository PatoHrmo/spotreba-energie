CREATE OR REPLACE PROCEDURE insert_SE_OSOBA(
	   rod_cislo IN SE_OSOBA.rod_cislo%TYPE,
     id_adresy IN SE_OSOBA.id_adresy%TYPE,
     meno IN SE_OSOBA.meno%TYPE,
     priezvisko IN SE_OSOBA.priezvisko%TYPE
     )
IS
BEGIN

  INSERT INTO SE_OSOBA VALUES (rod_cislo, id_adresy, meno, priezvisko);

  COMMIT;

END;
/