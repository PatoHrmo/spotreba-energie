set SERVEROUTPUT ON
begin
 for datumy in (select datum_instalacie, datum_odobratia, cis_zariadenia 
                from SE_HISTORIA
                where cislo_odberatela = 0
                and GET_TYP_VELICINY_ZARIADENIA(cis_zariadenia)='voda')
 loop
    for vypis in (select * from SE_odpis 
                  where  cis_zariadenia = datumy.cis_zariadenia
                  and datum_odpisu >= datumy.datum_instalacie
                  and datum_odpisu <= datumy.datum_odobratia)
    loop 
      dbms_output.put_line(vypis.cis_zariadenia||' '||datumy.datum_instalacie||' '||datumy.datum_odobratia||' '||vypis.datum_odpisu);
    end loop;
 end loop;
end;