set SERVEROUTPUT ON
create or replace procedure get_spotreba_za_obdobie(
  datum_od date,
  datum_do date,
  cislo_meraneho_odberatela SE_HISTORIA.CISLO_ODBERATELA%TYPE,
  skumana_meracia_velicina SE_TYP_ZARIADENIA.MERACIA_VELICINA%TYPE
)
is
begin
 
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