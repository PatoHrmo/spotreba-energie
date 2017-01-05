create or replace type zaznam_o_spotrebe_t is object(
  datum_od date,
  datum_do date,
  spotreba number
);
/
create or replace type zaznamy_o_spotrebe_t is table of zaznam_o_spotrebe_t;
/
create or replace function get_statistika_spotreby(
    pa_cislo_odberatela SE_ODBERATEL.cislo_odberatela%TYPE,
    datum_od date,
    datum_do date,
    granularita int,
    pa_velicina SE_TYP_ZARIADENIA.meracia_velicina%TYPE
)
return zaznamy_o_spotrebe_t
as 
zaznamy zaznamy_o_spotrebe_t;
zaznam zaznam_o_spotrebe_t;
pom_od date;
pom_do date;
pocet_mesiacov_granulity integer;
spotreba_v_obdoby number;
begin
  -- nastavenie intervalu datumov pre statistiku
  IF granularita=0 then
    pocet_mesiacov_granulity := 1;
  elsif granularita=1 then
    pocet_mesiacov_granulity := 6;
  else pocet_mesiacov_granulity := 12;
  end if;
  
  zaznamy := zaznamy_o_spotrebe_t();
  pom_od := datum_od;
  pom_do := ADD_MONTHS(pom_od,pocet_mesiacov_granulity);
  WHILE pom_do < datum_do
  LOOP
    spotreba_v_obdoby := get_spotreba_za_obdobie(pa_cislo_odberatela,pom_od,pom_do,pa_velicina);
    zaznamy.extend;
    zaznamy(zaznamy.last):= zaznam_o_spotrebe_t(pom_od,pom_do,spotreba_v_obdoby);
    pom_od := pom_do;
    pom_do := ADD_MONTHS(pom_od,pocet_mesiacov_granulity);
  END LOOP;
  return zaznamy;
end get_statistika_spotreby;