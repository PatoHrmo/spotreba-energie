create or replace function get_pocet_vymen_zariadenia(
  pa_cislo_odberatela SE_ODBERATEL.cislo_odberatela%TYPE,
  pa_datum_od date,
  pa_datum_do date,
  pa_meracia_velicina SE_TYP_ZARIADENIA.MERACIA_VELICINA%TYPE
)
return integer
is
pocet_vymen integer;
begin
  select count(*) into pocet_vymen from SE_HISTORIA his
        where his.cislo_odberatela=pa_cislo_odberatela
        and datum_odobratia is not null
        and datum_odobratia  between pa_datum_od and pa_datum_do
        and GET_TYP_VELICINY_ZARIADENIA(cis_zariadenia) = pa_meracia_velicina;
        return pocet_vymen;
end get_pocet_vymen_zariadenia;