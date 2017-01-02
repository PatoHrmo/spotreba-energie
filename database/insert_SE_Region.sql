CREATE OR REPLACE PROCEDURE insert_SE_Region(
	   nazov_regionu IN SE_region.nazov%TYPE
     )
IS
BEGIN

  INSERT INTO SE_region VALUES (seq_id_regionu.nextval, nazov_regionu);

  COMMIT;

END;
/