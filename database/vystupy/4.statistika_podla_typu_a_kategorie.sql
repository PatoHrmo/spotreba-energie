-- PRED COMPILOVANIM NAJPRV KOMPILUJ  POMOCNE_TYPY.SQL

------------------nazorna ukazka pouzitia- po kompilacii spusti toto
--select * from table(get_statistika_typ_a_kategoria(2013,'voda'));
create or replace function get_statistika_typ_a_kategoria(
  pa_rok integer,
  pa_velicina Varchar2
)
return zaznamy_spotreby_energie_t
as 

zaznamy zaznamy_spotreby_energie_t;
datum_max integer;
datum_min integer;
max_spotreba number;
min_spotreba number;
priem_spotreba number;
spotreba_v_mesiaci number;
begin
  zaznamy := zaznamy_spotreby_energie_t();
  for typ_odberatela in (select distinct typ from SE_ODBERATEL) loop
      for kategoria_odberatela in (select distinct kategoria from SE_ODBERATEL) loop
          priem_spotreba:=0;
          min_spotreba:=99999999999;
          max_spotreba:=-99999999999;
          for cislo_mesiaca in 1..12 loop
            spotreba_v_mesiaci:=0;
            for odberatel in (select * from SE_ODBERATEL 
                              where typ = typ_odberatela.typ 
                              and kategoria = kategoria_odberatela.kategoria)
            loop
                spotreba_v_mesiaci:=spotreba_v_mesiaci+
                      get_spotreba_za_obdobie(odberatel.cislo_odberatela,to_date('01.'||cislo_mesiaca||'.'||pa_rok,'dd.mm.yyyy'),
                                              ADD_MONTHS(to_date('01.'||cislo_mesiaca||'.'||pa_rok,'dd.mm.yyyy'),1),pa_velicina);
            end loop;
            if spotreba_v_mesiaci > max_spotreba then
              max_spotreba:=spotreba_v_mesiaci;
              datum_max:= cislo_mesiaca;
            end if;
            if spotreba_v_mesiaci < min_spotreba then
              min_spotreba:=spotreba_v_mesiaci;
              datum_min:= cislo_mesiaca;
            end if;
            priem_spotreba:= priem_spotreba+ spotreba_v_mesiaci;
          end loop;
          priem_spotreba:= priem_spotreba/12;
          zaznamy.extend;
          zaznamy(zaznamy.last):=zaznam_spotreba_energie_t(typ_odberatela.typ,kategoria_odberatela.kategoria
                                ,min_spotreba,datum_min,max_spotreba,datum_max,priem_spotreba);
      end loop;
  end loop;
  return zaznamy;
end get_statistika_typ_a_kategoria;