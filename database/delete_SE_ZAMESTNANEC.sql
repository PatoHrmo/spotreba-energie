create or replace procedure delete_SE_ZAMESTNANEC(
  id_mazaneho_zamestnanca SE_ZAMESTNANEC.id_zamestnanca%TYPE
)
is 

begin

UPDATE SE_ODPIS
SET id_zamestnanca = null
where id_zamestnanca = id_mazaneho_zamestnanca;

delete from SE_ZAMESTNANEC where id_zamestnanca = id_mazaneho_zamestnanca;
commit;

end;
/