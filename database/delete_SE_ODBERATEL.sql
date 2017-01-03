create or replace procedure delete_SE_ODBERATEL(
  cislo_mazaneho_odberatela SE_ODBERATEL.cislo_odberatela%TYPE
)
is 

begin

for hist_zaznam in (select * from SE_HISTORIA where cislo_odberatela = cislo_mazaneho_odberatela)
loop
  delete from se_odpis 
  where cis_zariadenia = hist_zaznam.cis_zariadenia
  and datum_odpisu<=hist_zaznam.datum_odobratia
  and datum_odpisu>=hist_zaznam.datum_instalacie;
end loop;

delete from SE_historia where cislo_odberatela = cislo_mazaneho_odberatela;

delete from SE_ODBERATEL where cislo_odberatela = cislo_mazaneho_odberatela;

commit;
end;
/