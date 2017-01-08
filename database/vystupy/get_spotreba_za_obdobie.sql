--set SERVEROUTPUT ON
--select GET_SPOTREBA_ZA_OBDOBIE(0,to_date('1.8.2000','dd.mm.yyyy'),to_date('1.8.2013','dd.mm.yyyy'),'voda') from dual;
create or replace function get_spotreba_za_obdobie(
    pa_cislo_odberatela SE_ODBERATEL.cislo_odberatela%TYPE,
    datum_od date,
    datum_do date,
    pa_velicina SE_TYP_ZARIADENIA.meracia_velicina%TYPE
)
return number
as 
datum_odpisu_pred_zaujmom date;
stav_pred_zaujmom number; 
existuje_odpis_pred_zaciatkom integer;
datum_odpisu_po_zaujme date;
stav_po_zaujme number; 
existuje_odpis_po_zaciatku integer;
domysleny_prvy_odpis number;
predosly_odpis number;
spotreba number;
begin
  --ZACIATOK ZISTOVANIA PRVEHO ODPISU
  -- zistovanie odpisu pred datum_od
  select max(datum_odpisu) into datum_odpisu_pred_zaujmom from SE_HISTORIA
  join SE_ZARIADENIE using(cis_zariadenia) join SE_ODPIS using (cis_zariadenia)
  where GET_TYP_VELICINY_ZARIADENIA(cis_zariadenia) = pa_velicina
  and cislo_odberatela = pa_cislo_odberatela
  and datum_odpisu between datum_instalacie and nvl(datum_odobratia,ADD_MONTHS(sysdate,12*700))
  and datum_odpisu<=datum_od;
  if datum_odpisu_pred_zaujmom is null then
    existuje_odpis_pred_zaciatkom:=0;
    --dbms_output.put_line('pred zaciatkom zaujmu nic nie je');
  else
    existuje_odpis_pred_zaciatkom:=1;
    --dbms_output.put_line('datum pred datum_od '||to_char(datum_odpisu_pred_zaujmom,'dd.mm.yyyy'));
    select min(stav) into stav_pred_zaujmom from SE_ODPIS  join SE_HISTORIA using(cis_zariadenia)
    where cislo_odberatela = pa_cislo_odberatela and DATUM_ODPISU = datum_odpisu_pred_zaujmom
    and GET_TYP_VELICINY_ZARIADENIA(cis_zariadenia) = pa_velicina;
    --dbms_output.put_line('stav pred zaciatkovym zaujmom '|| stav_pred_zaujmom);
  end if;
  
  --zistovanie odpisu po datum_od
  select min(datum_odpisu) into datum_odpisu_po_zaujme from SE_HISTORIA
  join SE_ZARIADENIE using(cis_zariadenia) join SE_ODPIS using (cis_zariadenia)
  where GET_TYP_VELICINY_ZARIADENIA(cis_zariadenia) = pa_velicina
  and cislo_odberatela = pa_cislo_odberatela
  and datum_odpisu between datum_instalacie and nvl(datum_odobratia,ADD_MONTHS(sysdate,12*700))
  and datum_odpisu >= datum_od ;
  if datum_odpisu_po_zaujme is null then
    existuje_odpis_po_zaciatku:=0;
    --dbms_output.put_line('po zaciatku od zaujmu nic nie je');
  else
    existuje_odpis_po_zaciatku:=1;
    --dbms_output.put_line('datum odpisu po datum_od'||to_char(datum_odpisu_po_zaujme,'dd.mm.yyyy'));
    select max(stav) into stav_po_zaujme from SE_ODPIS  join SE_HISTORIA using(cis_zariadenia)
    where cislo_odberatela = pa_cislo_odberatela and DATUM_ODPISU = datum_odpisu_po_zaujme
    and GET_TYP_VELICINY_ZARIADENIA(cis_zariadenia) = pa_velicina;
    --dbms_output.put_line('stav po datum_od'|| stav_po_zaujme);
  end if;
  
  -- ak rozmedzie zacina po odpisoch vratim 0;
  if existuje_odpis_po_zaciatku = 0 then
    return 0;
  end if;
  -- ak existuje odpis pred aj po tak vyratam hodnotu domysleneho odpisu
  if existuje_odpis_pred_zaciatkom=1 then
    -- ak su datumy rovnake, znamena to ze datum_od je presne datum nejakeho odpisu
    if datum_odpisu_pred_zaujmom = datum_odpisu_po_zaujme then
      -- ak sa vsak stavy odpisu v tento den nerovnaju, znamena to ze doslo k vymene prave dna datum_od
      -- cize viem ze je treba zobrat mensiu hodnotu, ktora bude vzdy 0
      if stav_po_zaujme <> stav_pred_zaujmom then
        domysleny_prvy_odpis:= 0;
      else
        domysleny_prvy_odpis:= stav_pred_zaujmom;
      end if;
    else
      -- pomocou rastu a poctu dni vyratam stav domysleneho prveho odpisu
      domysleny_prvy_odpis:= stav_pred_zaujmom + (datum_od-datum_odpisu_pred_zaujmom) * ((stav_po_zaujme-stav_pred_zaujmom)/(datum_odpisu_po_zaujme-datum_odpisu_pred_zaujmom));
    end if;
  else
    -- ak neexistuje odpis pred datum_od tak spotreba je 0
    domysleny_prvy_odpis:=0;
  end if;
  --KONIEC ZISTOVANIA PRVEHO ODPISU
  ---------RATANIE ODPISOV
  predosly_odpis:=domysleny_prvy_odpis;
  spotreba:=0;
  for odpis in (select datum_odpisu, stav, cis_zariadenia from SE_HISTORIA 
                join SE_ZARIADENIE using(cis_zariadenia) join SE_ODPIS using (cis_zariadenia)
                where GET_TYP_VELICINY_ZARIADENIA(cis_zariadenia) = pa_velicina
                and cislo_odberatela = pa_cislo_odberatela
                and datum_odpisu between datum_instalacie and nvl(datum_odobratia,ADD_MONTHS(sysdate,12*700))
                and datum_odpisu between datum_od and datum_do
                order by cis_zariadenia, stav) 
  loop
    if odpis.stav<predosly_odpis then
        spotreba:= spotreba+odpis.stav;
    else
        spotreba:=spotreba + odpis.stav-predosly_odpis;
    end if;
    predosly_odpis:=odpis.stav;
    --SYS.DBMS_OUTPUT.PUT_LINE('spotreba je '||spotreba);
  end loop;
  
  ---------KONIEC RATANIA ODPISOV
  --ZACIATOK ZISTOVANIA POSLEDNEHO ODPISU
  -- zistovanie odpisu pred datum_do
  select max(datum_odpisu) into datum_odpisu_pred_zaujmom from SE_HISTORIA 
  join SE_ZARIADENIE using(cis_zariadenia) join SE_ODPIS using (cis_zariadenia)
  where GET_TYP_VELICINY_ZARIADENIA(cis_zariadenia) = pa_velicina
  and cislo_odberatela = pa_cislo_odberatela
  and datum_odpisu between datum_instalacie and nvl(datum_odobratia,ADD_MONTHS(sysdate,12*700))
  and datum_odpisu<=datum_do;
  if datum_odpisu_pred_zaujmom is null then
    existuje_odpis_pred_zaciatkom:=0;
    --dbms_output.put_line('pred koncom nic nie je');
    return 0;
  else
    existuje_odpis_pred_zaciatkom:=1;
    --dbms_output.put_line('datum_pred datum_do '||to_char(datum_odpisu_pred_zaujmom,'dd.mm.yyyy'));
    select min(stav) into stav_pred_zaujmom from SE_ODPIS  join SE_HISTORIA using(cis_zariadenia)
    where cislo_odberatela = pa_cislo_odberatela and DATUM_ODPISU = datum_odpisu_pred_zaujmom
    and GET_TYP_VELICINY_ZARIADENIA(cis_zariadenia) = pa_velicina;
    --dbms_output.put_line('stav pred datum_do'|| stav_pred_zaujmom);
  end if;
  
  --zistovanie odpisu po datum_do
  select min(datum_odpisu) into datum_odpisu_po_zaujme from SE_HISTORIA
  join SE_ZARIADENIE using(cis_zariadenia) join SE_ODPIS using (cis_zariadenia)
  where GET_TYP_VELICINY_ZARIADENIA(cis_zariadenia) = pa_velicina
  and cislo_odberatela = pa_cislo_odberatela
  and datum_odpisu between datum_instalacie and nvl(datum_odobratia,ADD_MONTHS(sysdate,12*700))
  --????
  and datum_odpisu >= datum_do;
  if datum_odpisu_po_zaujme is null then
    existuje_odpis_po_zaciatku:=0;
    --dbms_output.put_line('po konci nic nie je');
  else
    existuje_odpis_po_zaciatku:=1;
    --dbms_output.put_line('datum po datum_do'||to_char(datum_odpisu_po_zaujme,'dd.mm.yyyy'));
    select max(stav) into stav_po_zaujme from SE_ODPIS  join SE_HISTORIA using(cis_zariadenia)
    where cislo_odberatela = pa_cislo_odberatela and DATUM_ODPISU = datum_odpisu_po_zaujme
    and GET_TYP_VELICINY_ZARIADENIA(cis_zariadenia) = pa_velicina;
   -- dbms_output.put_line('stav po zaujmom'|| stav_po_zaujme);
  end if;
  
  -- ak rozmedzie konci po odpisoch vratim spotrebu;
  if existuje_odpis_po_zaciatku = 0 then
    --dbms_output.put_line('spotreba je'|| spotreba);
    return spotreba;
  end if;
  -- ak existuje odpis pred aj po tak vyratam hodnotu domysleneho odpisu
  if existuje_odpis_pred_zaciatkom=1 then
    -- ak su datumy rovnake, znamena to ze datum_od je presne datum nejakeho odpisu
    if datum_odpisu_pred_zaujmom = datum_odpisu_po_zaujme then
      -- ak sa vsak stavy odpisu v tento den nerovnaju, znamena to ze doslo k vymene prave dna datum_do
      -- cize viem ze je treba zobrat vecsiu hodnotu, ktora bude vzdy stav_po_zaujme
      if stav_po_zaujme <> stav_pred_zaujmom then
        domysleny_prvy_odpis:= stav_po_zaujme;
      else
        domysleny_prvy_odpis:= stav_pred_zaujmom;
      end if;
    else
      -- pomocou rastu a poctu dni vyratam stav domysleneho posledneho odpisu
      domysleny_prvy_odpis:= stav_pred_zaujmom + (datum_do-datum_odpisu_pred_zaujmom) * ((stav_po_zaujme-stav_pred_zaujmom)/(datum_odpisu_po_zaujme-datum_odpisu_pred_zaujmom));
    end if;
  else
    -- ak neexistuje odpis pred datum_do tak spotreba je 0
    return 0;
  end if;
  --dbms_output.put_line('domysleny posledny odpis'|| domysleny_prvy_odpis);
  if domysleny_prvy_odpis<predosly_odpis then
        spotreba:= spotreba+domysleny_prvy_odpis;
    else
        spotreba:=spotreba + domysleny_prvy_odpis-predosly_odpis;
    end if;
    
   --KONIEC ZISTOVANIA POSLEDNEHO ODPISU
    --dbms_output.put_line('spotreba'|| spotreba);
  return spotreba;
end get_spotreba_za_obdobie;