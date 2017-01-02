CREATE OR REPLACE PROCEDURE insert_SE_Mesto(
	   id_regionu IN SE_mesto.id_regionu%TYPE,
     psc IN SE_mesto.psc%TYPE,
     nazov IN SE_mesto.nazov%TYPE
     )
IS
BEGIN

  INSERT INTO SE_mesto VALUES (seq_id_mesta.nextval, id_regionu, psc, nazov);

  COMMIT;

END;
/