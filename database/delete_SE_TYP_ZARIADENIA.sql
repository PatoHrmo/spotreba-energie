create or replace procedure delete_SE_TYP_ZARIADENIA(
  cislo_typu_zariadenia SE_ZARIADENIE.cis_zariadenia%TYPE
)
is 
begin
delete from SE_TYP_ZARIADENIA where typ = cislo_typu_zariadenia;

commit;
end;
/