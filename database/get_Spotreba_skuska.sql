
create or replace procedure skuska_get_spotreba_za_obdobie(
  datum_od date,
  datum_do date,
  cislo_meraneho_odberatela SE_HISTORIA.CISLO_ODBERATELA%TYPE,
  skumana_meracia_velicina SE_TYP_ZARIADENIA.MERACIA_VELICINA%TYPE
)
is
  type odpis_t is record(
    datum_odpisu SE_ODPIS.datum_odpisu%TYPE,
    cis_zariadenia SE_ODPIS.cis_zariadenia%TYPE,
    stav SE_ODPIS.stav%TYPE
  );
  type tabulka_odpisov_t is table of odpis_t;
  odpis_pred_hladanim odpis_t;
  odpis_po_hladani odpis_t;
  datum_prveho_odpisu date;
  datum_posledneho_odpisu date;
  tabulka_odpisov tabulka_odpisov_t;
  cur sys_refcursor;
  
begin
  
 --select max(datum_odpisu), min(datum_odpisu) into datum_posledneho_odpisu, datum_prveho_odpisu
 --from 
 for datumy in (select datum_instalacie, datum_odobratia, cis_zariadenia 
                from SE_HISTORIA
                where cislo_odberatela = 0
                and GET_TYP_VELICINY_ZARIADENIA(cis_zariadenia)=skumana_meracia_velicina)
 loop
    dbms_output.put_line(' ');
    dbms_output.put_line(datumy.cis_zariadenia||' '||datumy.datum_instalacie||' '||datumy.datum_odobratia);
    for vypis in (select * from SE_odpis 
                  where  cis_zariadenia = datumy.cis_zariadenia
                  and datum_odpisu >= datumy.datum_instalacie
                  and datum_odpisu <= NVL(datumy.datum_odobratia, TO_DATE('9999-01-01','yyyy-mm-dd'))
                  and datum_odpisu >= datum_od
                  and datum_odpisu <= datum_do)
    loop 
      dbms_output.put_line(vypis.datum_odpisu||' '||vypis.stav);
    end loop;
 end loop;
end;