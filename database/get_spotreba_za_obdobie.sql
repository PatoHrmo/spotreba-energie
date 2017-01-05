create or replace function get_spotreba_za_obdobie(
    pa_cislo_odberatela SE_ODBERATEL.cislo_odberatela%TYPE,
    datum_od date,
    datum_do date,
    pa_velicina SE_TYP_ZARIADENIA.velicina%TYPE
)
return number;
is 
begin
  select stav from SE_ODPIS join SE_HISTORIA USING (cis_zariadenia)
              join SE_ZARIADENIE USING (cis_zariadenia)
              join SE_TYP_ZARIADENIA USING (typ)
  where cislo_odberatela = pa_cislo_odberatela
        and velicina = pa_velicina
end;