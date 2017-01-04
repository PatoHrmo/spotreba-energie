CREATE OR REPLACE PROCEDURE update_SE_ADRESA(
     id_menenej_adresy SE_ADRESA.id_adresy%TYPE,
	   nove_id_mesta IN SE_adresa.id_mesta%TYPE,
     nove_cislo IN SE_adresa.cislo%TYPE,
     nova_ulica IN SE_adresa.ulica%TYPE
     )
IS
BEGIN

  UPDATE SE_ADRESA
  SET id_mesta = nove_id_mesta,
      cislo = nove_cislo,
      ulica = nova_ulica
  WHERE id_adresy = id_menenej_adresy;

  COMMIT;

END;
/