-- takto sa foto asi vkladat nebude
CREATE OR REPLACE PROCEDURE insert_SE_ZAMESTNANEC(
	   rod_cislo IN SE_ZAMESTNANEC.rod_cislo%TYPE,
     id_regionu IN SE_ZAMESTNANEC.id_regionu%TYPE,
     foto IN SE_ZAMESTNANEC.foto%TYPE
     )
IS
BEGIN

  INSERT INTO SE_ZAMESTNANEC VALUES (seq_id_zamestnanca.nextval, rod_cislo, id_regionu, foto);

  COMMIT;

END;
/