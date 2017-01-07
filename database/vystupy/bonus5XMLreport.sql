-- pred kompilovanim je nutne mat najpr skompilovane get_spotreba_za_obdobie.xml
-- priklad pouzitia select get_xml_odberatela(0) from dual; vracia clob repreyentujuci xml reportu
create or replace function get_xml_odberatela(
    pa_cislo_odberatela SE_ODBERATEL.cislo_odberatela%TYPE
    )
return clob
is 
vysledok clob;
meno_odb varchar2(100);
nazov_firmy_odb Varchar2(100);
je_osoba integer;
begin
select count(*) into je_osoba from SE_ODBERATEL where cislo_odberatela = pa_cislo_odberatela and ico is null;


vysledok:='<odberatel>'||chr(13)||chr(10);
vysledok:=vysledok||'   <cislo_odberatela>'||pa_cislo_odberatela||'</cislo_odberatela>'||chr(13)||chr(10);

if je_osoba=1 then
  select meno||' '||priezvisko into meno_odb from SE_ODBERATEL join SE_OSOBA using(rod_cislo) where cislo_odberatela = pa_cislo_odberatela;
  vysledok:=vysledok||'   <meno_odberatela>'||meno_odb||'</meno_odberatela>'||chr(13)||chr(10);
else
 select nazov_firmy into nazov_firmy_odb from SE_FIRMA join SE_ODBERATEL using (ico) where cislo_odberatela = pa_cislo_odberatela;
 vysledok:=vysledok||'   <nazov_firmy>'||nazov_firmy_odb||'</nazov_firmy>'||chr(13)||chr(10);
end if;
vysledok:=vysledok||'   <odpisy>'||chr(13)||chr(10);
for velicina in (select distinct meracia_velicina from SE_TYP_ZARIADENIA) loop
    vysledok:=vysledok||'      <'||velicina.meracia_velicina||'>'||chr(13)||chr(10);
    vysledok:=vysledok||'         <spotreba>'||GET_SPOTREBA_ZA_OBDOBIE(pa_cislo_odberatela,trunc(sysdate-365,'yyyy'),trunc(sysdate,'yyyy'),velicina.meracia_velicina)||'</spotreba>'||chr(13)||chr(10);
    for odpis in (select stav, datum_odpisu from SE_ODPIS join SE_ZARIADENIE using(cis_zariadenia) join SE_HISTORIA using(cis_zariadenia)
                  where get_typ_veliciny_zariadenia(cis_zariadenia) = velicina.meracia_velicina
                  and datum_odpisu between datum_instalacie and nvl(datum_odobratia,ADD_MONTHS(sysdate,12*700))
                  and cislo_odberatela = pa_cislo_odberatela
                  and datum_odpisu between trunc(sysdate-365,'yyyy') and trunc(sysdate,'yyyy')) loop
        vysledok:=vysledok||'         <odpis>'||chr(13)||chr(10);
        vysledok:=vysledok||'            <datum>'||to_char(odpis.datum_odpisu,'dd.mm.yyyy')||'</datum>'||chr(13)||chr(10);
        vysledok:=vysledok||'            <stav>'||odpis.stav||'</stav>'||chr(13)||chr(10);
        vysledok:=vysledok||'         </odpis>'||chr(13)||chr(10);
    end loop;
    
    vysledok:=vysledok||'      </'||velicina.meracia_velicina||'>'||chr(13)||chr(10);
end loop;
vysledok:=vysledok||'   </odpisy>'||chr(13)||chr(10);
vysledok:=vysledok||'   <servisy>'||chr(13)||chr(10);
for servis in (select datum_servisu, info_servisu 
               from SE_SERVIS join SE_ZARIADENIE using(cis_zariadenia) join SE_HISTORIA using(cis_zariadenia)
               where datum_servisu between datum_instalacie and nvl(datum_odobratia,ADD_MONTHS(sysdate,12*700))
                  and cislo_odberatela = pa_cislo_odberatela
                  and datum_servisu between trunc(sysdate-365,'yyyy') and trunc(sysdate,'yyyy')) loop
    vysledok:=vysledok||'      <servis>'||chr(13)||chr(10);
    vysledok:=vysledok||'            <datum>'||to_char(servis.datum_servisu,'dd.mm.yyyy')||'</datum>'||chr(13)||chr(10);
    vysledok:=vysledok||'            <popis>'||servis.info_servisu.popis||'</popis>'||chr(13)||chr(10);
    if servis.info_servisu.typ_servisu = '0'  then
        vysledok:=vysledok||'            <typ>Diagnostika</typ>'||chr(13)||chr(10);
    elsif servis.info_servisu.typ_servisu = '1' then
        vysledok:=vysledok||'            <typ>Oprava</typ>'||chr(13)||chr(10);
    else
        vysledok:=vysledok||'            <typ>Nastavenie</typ>'||chr(13)||chr(10);
    end if;
    vysledok:=vysledok||'      </servis>'||chr(13)||chr(10);
end loop;
vysledok:=vysledok||'   </servisy>'||chr(13)||chr(10);
vysledok:=vysledok||'</odberatel>';
return vysledok;
end get_xml_odberatela;