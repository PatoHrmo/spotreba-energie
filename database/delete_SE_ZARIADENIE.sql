create or replace procedure delete_SE_ZARIADENIE(
  cislo_odstraneneho_zariadenia SE_ZARIADENIE.cis_zariadenia%TYPE
)
is 
begin
delete from SE_SERVIS where cis_zariadenia = cislo_odstraneneho_zariadenia;
delete from SE_ODPIS where cis_zariadenia = cislo_odstraneneho_zariadenia;
delete from SE_HISTORIA where cis_zariadenia = cislo_odstraneneho_zariadenia;
delete from SE_zariadenie where cis_zariadenia = cislo_odstraneneho_zariadenia;
commit;
end;