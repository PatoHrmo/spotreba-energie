create or replace procedure delete_SE_REGION(
  id_mazaneho_regionu SE_REGION.id_regionu%TYPE
)
is 

begin
for id_miest in (select id_mesta from SE_MESTO where id_regionu = id_mazaneho_regionu) loop
  delete_SE_MESTO(id_miest.id_mesta);
end loop;

delete from SE_REGION where id_regionu = id_mazaneho_regionu;
commit;
end;
/