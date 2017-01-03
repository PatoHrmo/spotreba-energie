create or replace procedure delete_SE_ADRESA(
  id_mazanej_adresy SE_ADRESA.id_adresy%TYPE
)
is 

begin
for ica in (select ico from SE_FIRMA where id_adresy = id_mazanej_adresy) loop
  delete_SE_FIRMA(ica.ico);
end loop;

for rodcisla in (select rod_cislo from SE_OSOBA where id_adresy = id_mazanej_adresy) loop
  delete_SE_OSOBA(rodcisla.rod_cislo);
end loop;


delete from SE_ADRESA where id_adresy = id_mazanej_adresy;
commit;
end;
/