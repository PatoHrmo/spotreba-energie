create or replace procedure odober_zariadenie (
  cislo_odberatela_vlastnika SE_HISTORIA.cislo_odberatela%TYPE,
  cislo_odoberaneho_zariadenia SE_HISTORIA.cis_zariadenia%TYPE,
  datum_odberu_zariadenia SE_HISTORIA.datum_odobratia%TYPE,
  id_odoberajuceho_zamestnanca SE_ODPIS.id_zamestnanca%TYPE,
  spotreba_pred_odobratim SE_ODPIS.stav%TYPE)
  is
  zariadenie_sa_nepouziva EXCEPTION;
  
  begin
  IF je_zariadenie_pouzivane(cislo_odoberaneho_zariadenia)=0 THEN
  raise zariadenie_sa_nepouziva;
  end if;
  
  update SE_HISTORIA
  SET datum_odobratia = datum_odberu_zariadenia
  where cis_zariadenia = cislo_odoberaneho_zariadenia
  and datum_odobratia is null;
  
  insert_SE_ODPIS(datum_odberu_zariadenia,cislo_odoberaneho_zariadenia,id_odoberajuceho_zamestnanca,spotreba_pred_odobratim);
  
  end;
  /