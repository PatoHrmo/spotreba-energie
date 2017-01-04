CREATE OR REPLACE PROCEDURE update_SE_TYP_ZARIADENIA(
     meneny_typ SE_TYP_ZARIADENIA.typ%TYPE,
     nove_cislo_modelu IN SE_TYP_ZARIADENIA.cislo_modelu%TYPE,
     novy_vyrobca IN SE_TYP_ZARIADENIA.vyrobca%TYPE
     )
IS
BEGIN

  UPDATE SE_TYP_ZARIADENIA 
  SET cislo_modelu = nove_cislo_modelu,
  vyrobca = novy_vyrobca
  where typ = meneny_typ;

  COMMIT;

END;
/