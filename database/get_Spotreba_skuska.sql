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
TYPE spotreba_odpis_record IS RECORD (
      datum_odpisu SE_ODPIS.datum_odpisu%TYPE,
      cis_zariadenia SE_ZARIADENIE.cis_zariadenia%TYPE,
      stav SE_ODPIS.stav%TYPE
      );
TYPE spotreba_odpis_table IS TABLE OF spotreba_odpis_record;
odpisy spotreba_odpis_table;
odpis_pred spotreba_odpis_record;
odpis_po spotreba_odpis_record;
odpis spotreba_odpis_record;
spotreba_prev NUMBER;
spotreba integer;
zariadenie_prev NUMBER;
is_odpis_pred boolean;
is_odpis_po boolean;
begin
    -- ziskam si vöetky odpisy danÈho pouûÌvateæa v danom Ëasomvom rozmedzÌ
    select datum_odpisu, cis_zariadenia, stav bulk collect into odpisy from SE_HISTORIA
      join SE_ZARIADENIE using(cis_zariadenia) join SE_ODPIS using (cis_zariadenia)
    where GET_TYP_VELICINY_ZARIADENIA(cis_zariadenia) = pa_velicina
      and cislo_odberatela = pa_cislo_odberatela
      and datum_odpisu between datum_instalacie and nvl(datum_odobratia,ADD_MONTHS(sysdate,12*700))
      and datum_odpisu BETWEEN datum_od and datum_do order by datum_instalacie, datum_odpisu;
    --ZistÌm Ëi existuje eöte nejak˝ odpis pred dan˝m d·tumom
    BEGIN
      SELECT datum_odpisu, cis_zariadenia, stav INTO odpis_pred FROM (
        SELECT datum_odpisu, cis_zariadenia, stav
          from SE_HISTORIA
          join SE_ZARIADENIE using(cis_zariadenia) join SE_ODPIS using (cis_zariadenia)
          WHERE DATUM_ODPISU < datum_od
          and cislo_odberatela = pa_cislo_odberatela
          and GET_TYP_VELICINY_ZARIADENIA(cis_zariadenia) = pa_velicina
          order by datum_odpisu desc, datum_instalacie desc) WHERE ROWNUM <= 1;
      is_odpis_pred := true;
      spotreba_prev := odpis_pred.stav;
      zariadenie_prev := odpis_pred.cis_zariadenia;   
      EXCEPTION
      WHEN NO_DATA_FOUND THEN
        spotreba_prev := 0;
        zariadenie_prev := -1;
        is_odpis_pred := false;
    END;
    
    --zistim Ëi existuje odpis po
    BEGIN
      SELECT datum_odpisu, cis_zariadenia, stav INTO odpis_po FROM (
        SELECT datum_odpisu, cis_zariadenia, stav
          from SE_HISTORIA
          join SE_ZARIADENIE using(cis_zariadenia) join SE_ODPIS using (cis_zariadenia)
          WHERE DATUM_ODPISU > datum_do
          and cislo_odberatela = pa_cislo_odberatela
          and GET_TYP_VELICINY_ZARIADENIA(cis_zariadenia) = pa_velicina
          order by datum_odpisu asc, datum_instalacie asc) WHERE ROWNUM <= 1;
      is_odpis_po := true;
      EXCEPTION
      WHEN NO_DATA_FOUND THEN
        is_odpis_po := false;
    END;
    spotreba := 0;
    IF(NVL(odpisy.last(), 0) > 0) THEN
      FOR i IN 1 .. odpisy.last()
      LOOP
      
        IF(odpisy.exists(i)) THEN
          odpis := odpisy(i);
          IF(zariadenie_prev != odpis.cis_zariadenia) THEN
            -- m·m odpis k dalöiemu zariadeniu a teda ma hodnota predch·dzaj˙ceho nezaujÌma
            zariadenie_prev := odpis.cis_zariadenia;
            spotreba_prev := 0;
          
          END IF;
        
          spotreba := spotreba + (odpis.stav - spotreba_prev); --v kaûdom kroku pripoËÌtavam prÌrastok medzi dvoma odpismi
          spotreba_prev := odpis.stav;
        
        END IF;
      END LOOP;
    END IF;
    --Ak mam znamy odpis pred, tak odpocitam pomernu Ëasù ktor· patrÌ rozsahu datumu ktor˝ ma nezaujÌma
    IF(is_odpis_pred AND NVL(odpisy.last(), 0) > 0) THEN
      spotreba := spotreba - ((odpisy(1).stav - odpis_pred.stav)/(odpisy(1).datum_odpisu - odpis_pred.datum_odpisu)*(datum_od - odpis_pred.datum_odpisu));
    END IF;
    --Ak mam znamy odpis po, tak pripocitam pomernu Ëasù ktor· patrÌ rozsahu datumu ktor˝ ma eöte zaujÌma
    IF(is_odpis_po) THEN
      -- Ak mam odpisy poËÌtam z poslednÈho odpisu
      IF(NVL(odpisy.last(), 0) > 0) THEN
        spotreba := spotreba + ((odpis_po.stav - odpisy(odpisy.last()).stav)/(odpis_po.datum_odpisu - odpisy(odpisy.last()).datum_odpisu)*(datum_do - odpisy(odpisy.last()).datum_odpisu));
      END IF;
      -- Ak sice nema odpisy ale m·m odpis pred viem vyjadriù pomernu Ëasù
      IF(is_odpis_pred AND NVL(odpisy.last(), 0) = 0 AND is_odpis_pred) THEN
        spotreba := spotreba + ((odpis_po.stav - odpis_pred.stav)/(odpis_po.datum_odpisu - odpis_pred.datum_odpisu)*(datum_do - datum_od ));
      END IF;
    END IF;
    
  return spotreba;
end get_spotreba_za_obdobie;