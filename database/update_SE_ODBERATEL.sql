
CREATE OR REPLACE PROCEDURE update_SE_ODBERATEL(
	   cislo_meneneho_odberatela IN SE_ODBERATEL.cislo_odberatela%TYPE,
     nova_kategoria IN SE_ODBERATEL.kategoria%TYPE
     )
IS
BEGIN

  UPDATE SE_ODBERATEL
  SET kategoria = nova_kategoria
  WHERE cislo_odberatela = cislo_meneneho_odberatela;

  COMMIT;

END;
/