

CREATE OR REPLACE PROCEDURE update_SE_ZAMESTNANEC(
	   id_meneneho_zamestnanca IN SE_ZAMESTNANEC.id_zamestnanca%TYPE,
     nove_id_regionu IN SE_ZAMESTNANEC.id_regionu%TYPE,
     nove_foto IN SE_ZAMESTNANEC.foto%TYPE
     )
IS
BEGIN

  UPDATE SE_ZAMESTNANEC 
  SET id_regionu = nove_id_regionu,
  foto = nove_foto
  WHERE id_zamestnanca = id_meneneho_zamestnanca;

  COMMIT;

END;
/