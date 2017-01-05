create or replace function get_spotreba_za_obdobie(
    pa_cislo_odberatela SE_ODBERATEL.cislo_odberatela%TYPE,
    datum_od date,
    datum_do date,
    pa_velicina SE_TYP_ZARIADENIA.meracia_velicina%TYPE
)
return number
as 
begin
  
  return dbms_random.value(1,50);
end get_spotreba_za_obdobie;