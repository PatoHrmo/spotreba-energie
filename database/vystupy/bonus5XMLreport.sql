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
begin
--select meno||' '||priezvisko into meno_odb from SE_ODBERATEL join SE_OSOBA using(rod_cislo) where cislo_odberatela = pa_cislo_odberatela;
--select nazov_firmy into nazov_firmy_odb from SE_FIRMA join SE_ODBERATEL using (ico) where cislo_odberatela = pa_cislo_odberatela;

vysledok:='<odberatel>'||chr(13)||chr(10);
vysledok:=vysledok||'   <cislo_odberatela>'||pa_cislo_odberatela||'</cislo_odberatela>'||chr(13)||chr(10);

vysledok:=vysledok||'</odberatel>';
return vysledok;
end get_xml_odberatela;