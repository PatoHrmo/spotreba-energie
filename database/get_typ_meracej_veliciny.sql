-- vrati typ meracej veliciny danneho pristroja (identifikovaneho cislom)
create or replace function get_typ_veliciny_zariadenia(
      cislo_zariadenia SE_ZARIADENIE.cis_zariadenia%TYPE 
      )
return SE_TYP_ZARIADENIA.meracia_velicina%TYPE
IS
velicina SE_TYP_ZARIADENIA.meracia_velicina%TYPE;
begin
select meracia_velicina into velicina 
from SE_zariadenie join SE_typ_zariadenia using(typ)
where cis_zariadenia = cislo_zariadenia;
return velicina;
end;
/      