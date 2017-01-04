CREATE OR REPLACE TRIGGER uprava_rc_zamestnanec
BEFORE UPDATE
   ON SE_OSOBA
   FOR EACH ROW

DECLARE
BEGIN
  update SE_ZAMESTNANEC
  SET rod_cislo = :new.rod_cislo
  WHERE rod_cislo = :old.rod_cislo;
END;
/

CREATE OR REPLACE TRIGGER uprava_rc_odberatel
BEFORE UPDATE
   ON SE_OSOBA
   FOR EACH ROW

DECLARE
BEGIN
  update SE_ODBERATEL
  SET rod_cislo = :new.rod_cislo
  WHERE rod_cislo = :old.rod_cislo;
END;
/

CREATE OR REPLACE PROCEDURE update_SE_OSOBA(
     stare_rod_cislo SE_OSOBA.rod_cislo%TYPE,
	   nove_rod_cislo IN SE_OSOBA.rod_cislo%TYPE,
     nove_id_adresy IN SE_OSOBA.id_adresy%TYPE,
     nove_meno IN SE_OSOBA.meno%TYPE,
     nove_priezvisko IN SE_OSOBA.priezvisko%TYPE
     )
IS
BEGIN

  UPDATE SE_OSOBA
  SET rod_cislo = nove_rod_cislo,
  id_adresy = nove_id_adresy,
  meno = nove_meno,
  priezvisko = nove_priezvisko
  WHERE rod_cislo = stare_rod_cislo;

  COMMIT;

END;
/

