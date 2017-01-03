create or replace procedure update_SE_REGION (
  id_upravovaneho_regionu SE_REGION.id_regionu%TYPE,
  novy_nazov SE_REGION.nazov%TYPE
)
IS 
begin
  UPDATE SE_REGION
  SET nazov = novy_nazov
  where id_regionu = id_upravovaneho_regionu;
end;
/