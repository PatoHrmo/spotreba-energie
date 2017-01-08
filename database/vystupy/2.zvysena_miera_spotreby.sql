-- PRED COMPILOVANIM NAJPRV KOMPILUJ  POMOCNE_TYPY.SQL

------------------nazorna ukazka pouzitia- po kompilacii spusti toto
--select * from table(get_zvysena_miera_spotreby(to_date('10.10.2012','dd.mm.yyyy'),to_date('10.10.2013','dd.mm.yyyy'),1.2));
create or replace function get_zvysena_miera_spotreby(
    datum_od date,
    datum_do date,
    faktor_zvysenia number
)
return zaznamy_zvysenej_spotreby_t
as 
zaznamy zaznamy_zvysenej_spotreby_t;
zaznam zvysena_spotreba_t;
prvy_odpis date;
meno_odb varchar(40);
spotreba_v_minulosti_den number;
spotreba_v_sled_obdoby_den number;
begin
  zaznamy := zaznamy_zvysenej_spotreby_t();
  for odberatel in (select * from SE_ODBERATEL where rod_cislo is not null) loop
      for velicina in (select distinct meracia_velicina from SE_TYP_ZARIADENIA) loop
          select min(datum_instalacie) into prvy_odpis from SE_HISTORIA
          where cislo_odberatela = odberatel.cislo_odberatela
          and get_typ_veliciny_zariadenia(cis_zariadenia) = velicina.meracia_velicina;
          spotreba_v_minulosti_den:= (get_spotreba_za_obdobie(odberatel.cislo_odberatela,prvy_odpis,datum_od,velicina.meracia_velicina)/(datum_od-prvy_odpis));
          spotreba_v_sled_obdoby_den:=get_spotreba_za_obdobie(odberatel.cislo_odberatela,datum_od,datum_do,velicina.meracia_velicina)/(datum_do-datum_od);
          IF  spotreba_v_sled_obdoby_den> spotreba_v_minulosti_den*faktor_zvysenia
          THEN
             select meno||' '||priezvisko into meno_odb from SE_OSOBA where rod_cislo = odberatel.rod_cislo;
             zaznamy.extend;
             zaznamy(zaznamy.last):= zvysena_spotreba_t(meno_odb,velicina.meracia_velicina, 
                                     spotreba_v_minulosti_den,
                                     spotreba_v_sled_obdoby_den);
             
          END IF;
      end loop;
  end loop;
  
  return zaznamy;
end get_zvysena_miera_spotreby;