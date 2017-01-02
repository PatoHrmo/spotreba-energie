
CREATE OR REPLACE PROCEDURE insert_SE_TYP_ZARIADENIA(
     meracia_velicina IN SE_TYP_ZARIADENIA.meracia_velicina%TYPE,
     cislo_modelu IN SE_TYP_ZARIADENIA.cislo_modelu%TYPE,
     vyrobca IN SE_TYP_ZARIADENIA.vyrobca%TYPE
     )
IS
BEGIN

  INSERT INTO SE_TYP_ZARIADENIA VALUES (seq_typ_zariadenia.nextval, meracia_velicina, cislo_modelu, vyrobca);

  COMMIT;

END;
/