-- PRED COMPILOVANIM NAJPRV KOMPILUJ  POMOCNE_TYPY.SQL
----Tato funkcia vracia objekt so styrmi atributmy - vid priklad, je mozne ich vybrat aj v jednom select len ten by bol dlhy
---- preto v priklade ukazujem ako sa dostat ku kazdemu z nich
------------------nazorna ukazka pouzitia- po kompilacii spusti toto
--select get_najm_najve_spotreba('D','A',0,'voda',to_date('10.10.2013','dd.mm.yyyy'),to_date('10.10.2015','dd.mm.yyyy')).najvacsia_spotreba from dual;
--select get_najm_najve_spotreba('D','A',0,'voda',to_date('10.10.2013','dd.mm.yyyy'),to_date('10.10.2015','dd.mm.yyyy')).mesiac_najvecsia_spotreba from dual;
--select get_najm_najve_spotreba('D','A',0,'voda',to_date('10.10.2013','dd.mm.yyyy'),to_date('10.10.2015','dd.mm.yyyy')).najmensia_spotreba from dual;
--select get_najm_najve_spotreba('D','A',0,'voda',to_date('10.10.2013','dd.mm.yyyy'),to_date('10.10.2015','dd.mm.yyyy')).mesiac_najmensia_spotreba from dual;
create or replace function get_najm_najve_spotreba(
  pa_typ_odberatela char,
  pa_kategoria_odberatela char,
  pa_id_regionu integer,
  pa_velicina Varchar2,
  pa_od date,
  pa_do date
)
return naj_spotreba_t
as 
zaznamy zaznamy_spotreby_energie_t;
spotreba_dokopy number;
datum_od date := TRUNC(pa_od, 'MONTH');
datum_do date := ADD_MONTHS(datum_od,1);
datum_najmensej_spotreby date;
datum_najvecsej_spotreby date;
max_spotreba number :=-99999999999;
min_spotreba number :=99999999999;
begin
  
  while datum_od<pa_do loop
    spotreba_dokopy := 0;
    if pa_typ_odberatela='D' then
        for cis in (select cislo_odberatela from SE_ODBERATEL join SE_OSOBA using (rod_cislo)
                join SE_ADRESA using(id_adresy) join SE_MESTO using (id_mesta)
                where typ = 'D'
                and kategoria = pa_kategoria_odberatela
                and id_regionu = pa_id_regionu)
        loop
           --dbms_output.put_line('asd'||get_spotreba_za_obdobie(cis.cislo_odberatela,datum_od,datum_do,pa_velicina));
           spotreba_dokopy:= spotreba_dokopy + get_spotreba_za_obdobie(cis.cislo_odberatela,datum_od,datum_do,pa_velicina);
        end loop;
    end if;
    if pa_typ_odberatela='F' then
        for cis in (select cislo_odberatela from SE_ODBERATEL join SE_FIRMA using (ico)
                join SE_ADRESA using(id_adresy) join SE_MESTO using (id_mesta)
                where typ = 'F'
                and kategoria = pa_kategoria_odberatela
                and id_regionu = pa_id_regionu)
        loop
           --dbms_output.put_line('asd'||get_spotreba_za_obdobie(cis.cislo_odberatela,datum_od,datum_do,pa_velicina));
           spotreba_dokopy:= spotreba_dokopy + get_spotreba_za_obdobie(cis.cislo_odberatela,datum_od,datum_do,pa_velicina);
        end loop;
    end if;
    
    IF spotreba_dokopy> max_spotreba then
      max_spotreba:= spotreba_dokopy;
      datum_najvecsej_spotreby := datum_od;
    end if;
    IF spotreba_dokopy< min_spotreba then
      min_spotreba:= spotreba_dokopy;
      datum_najmensej_spotreby := datum_od;
    end if;
    datum_od:=datum_do;
    datum_do:= ADD_MONTHS(datum_do,1);
  end loop;
  
  
  return naj_spotreba_t(datum_najvecsej_spotreby,max_spotreba,datum_najmensej_spotreby,min_spotreba);
end get_najm_najve_spotreba;