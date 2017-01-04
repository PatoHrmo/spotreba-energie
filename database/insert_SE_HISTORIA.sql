
CREATE OR REPLACE PROCEDURE insert_SE_HISTORIA(
     nove_cislo_odberatela IN SE_HISTORIA.cislo_odberatela%TYPE,
     nove_cislo_zariadenia IN SE_HISTORIA.cis_zariadenia%TYPE,
     novy_datum_instalacie IN SE_HISTORIA.datum_instalacie%TYPE
     )
IS
pouziva_sa integer;
velicina_sa_uz_meria integer;
zariadenie_sa_pouziva EXCEPTION;
velicina_sa_meria EXCEPTION;
instalacia_pred_sucasnou EXCEPTION;
typ_meranej_veliciny_pristroja SE_TYP_ZARIADENIA.meracia_velicina%TYPE;
max_datum_odobratia date;
BEGIN
  -- kontrola ci nahodou odberatelovi nejdeme namontovat do domu zariadenie ktore ma iny odberatel
  IF JE_ZARIADENIE_POUZIVANE(nove_cislo_zariadenia)=1 THEN
  RAISE zariadenie_sa_pouziva;
  end if;
  -- kontrola ci sa uz meria velicina, ktoru ma merat nove zariadenie. V dome niesu dve merace napr. na vodu.
  select count(*) into velicina_sa_uz_meria 
  from dual where GET_TYP_VELICINY_ZARIADENIA(nove_cislo_zariadenia) IN (select GET_TYP_VELICINY_ZARIADENIA(cis_zariadenia) from SE_HISTORIA
  where CISLO_ODBERATELA=nove_cislo_odberatela and JE_ZARIADENIE_POUZIVANE(cis_zariadenia)=1);
  
  IF velicina_sa_uz_meria=1 THEN
  RAISE velicina_sa_meria;
  end if;
  -- kontrola ci nejdeme nainstalovat zariadenie do minulosti. napr uzivatel mal zariadenie na vodu
  -- 1.1.2015 až do 1.1.2017 a my sa pokusime nainstalovat zariadenie 1.1.2016
  select max(datum_odobratia) into max_datum_odobratia
  from SE_historia
  where cislo_odberatela = nove_cislo_odberatela
  and GET_TYP_VELICINY_ZARIADENIA(nove_cislo_zariadenia) = GET_TYP_VELICINY_ZARIADENIA(cis_zariadenia);
  
  IF novy_datum_instalacie < max_datum_odobratia then
  raise instalacia_pred_sucasnou;
  end if;
  
  -- vynulujeme zariadenie ktore ideme nainstalovat 
  UPDATE SE_ZARIADENIE 
  SET spotreba = 0
  where CIS_ZARIADENIA = nove_cislo_zariadenia;
  INSERT INTO SE_HISTORIA VALUES (nove_cislo_odberatela, nove_cislo_zariadenia, novy_datum_instalacie, null);
  
  EXCEPTION
    WHEN zariadenie_sa_pouziva THEN
        RAISE_APPLICATION_ERROR(-20001,'zariadenie sa pouziva');
    WHEN instalacia_pred_sucasnou THEN
        RAISE_APPLICATION_ERROR(-20002,'nemozno instalovat pred instalaciov sucasnych');
    WHEN velicina_sa_meria THEN
        RAISE_APPLICATION_ERROR(-20003,'odberatel uz ma pristroj na meranie tejto veliciny');
  COMMIT;

END;
/