
CREATE OR REPLACE PROCEDURE insert_SE_HISTORIA(
     nove_cislo_odberatela IN SE_HISTORIA.cislo_odberatela%TYPE,
     nove_cislo_zariadenia IN SE_HISTORIA.cis_zariadenia%TYPE,
     novy_datum_instalacie IN SE_HISTORIA.datum_instalacie%TYPE,
     id_instalujuceho_zamestnanca IN SE_ZAMESTNANEC.id_zamestnanca%TYPE
     )
IS
pouziva_sa integer;
velicina_sa_uz_meria integer;
zariadenie_sa_pouziva EXCEPTION;
velicina_sa_meria EXCEPTION;
instalacia_pred_sucasnou EXCEPTION;
zly_region_zamestnanca EXCEPTION;
id_regionu_zamestnanca SE_ZAMESTNANEC.id_regionu%TYPE;
id_regionu_odberatela SE_REGION.id_regionu%TYPE;
typ_meranej_veliciny_pristroja SE_TYP_ZARIADENIA.meracia_velicina%TYPE;
max_datum_odobratia date;
ico_odberatela SE_ODBERATEL.ico%TYPE; 
rc SE_OSOBA.rod_cislo%TYPE;
BEGIN
  -- ziskavanie id_regionu zamestnanca vykonavajuceho vymenu
  select id_regionu into id_regionu_zamestnanca 
  from SE_ZAMESTNANEC where id_zamestnanca = id_instalujuceho_zamestnanca;
  -- ziskavaie id regionu odberatela
  select ico into ico_odberatela from SE_ODBERATEL where cislo_odberatela = nove_cislo_odberatela;
  IF ico_odberatela is not null then
      select id_regionu into id_regionu_odberatela from SE_ODBERATEL
      join SE_FIRMA using(ico) join SE_ADRESA using (id_adresy)
      join SE_MESTO using (id_mesta) 
      where ico = ico_odberatela;
  ELSE
      select rod_cislo into rc from SE_ODBERATEL where cislo_odberatela = nove_cislo_odberatela;
      select id_regionu into id_regionu_odberatela from SE_ODBERATEL
      join SE_OSOBA using(rod_cislo) join SE_ADRESA using (id_adresy)
      join SE_MESTO using (id_mesta) 
      where rod_cislo = rc;
  end if;
  -- ak zamestnanec a odberatel nie su z rovankeho regionu, vyhod vynimku
  IF id_regionu_odberatela <> id_regionu_zamestnanca then
      raise zly_region_zamestnanca;
  end if;
  
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
    WHEN zly_region_zamestnanca THEN
        RAISE_APPLICATION_ERROR(-20004,'odberatel je z ineho regionu ako zamestnanec');
  COMMIT;

END;
/