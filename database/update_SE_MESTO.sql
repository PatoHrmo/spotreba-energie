CREATE OR REPLACE PROCEDURE update_SE_Mesto(
     id_meneneho_mesta SE_mesto.id_mesta%TYPE,
	   nove_id_regionu IN SE_mesto.id_regionu%TYPE,
     nove_psc IN SE_mesto.psc%TYPE,
     novy_nazov IN SE_mesto.nazov%TYPE
     )
IS
BEGIN

  UPDATE SE_MESTO
  SET id_regionu = nove_id_regionu,
      psc = nove_psc,
      nazov = novy_nazov
  where id_mesta = id_meneneho_mesta;

  COMMIT;

END;
/