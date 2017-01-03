create or replace procedure delete_SE_MESTO(
  id_mazaneho_mesta SE_MESTO.id_mesta%TYPE
)
is 

begin
for id_adries in (select id_adresy from SE_ADRESA where id_mesta = id_mazaneho_mesta) loop
  delete_SE_ADRESA(id_adries.id_adresy);
end loop;

delete from SE_MESTO where id_mesta = id_mazaneho_mesta;
commit;
end;
/