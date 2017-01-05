-- vrati 1 ak je zaridenie u niekoho namontovane, inak 0
create or replace function je_zariadenie_pouzivane(
      cislo_zariadenia SE_ZARIADENIE.cis_zariadenia%TYPE 
      )
return number
IS
je_pouzivane number;
begin
select count(CIS_ZARIADENIA) into je_pouzivane from SE_historia 
  where datum_odobratia is null
  and CIS_ZARIADENIA=cislo_zariadenia;
  return je_pouzivane;
end;
/      