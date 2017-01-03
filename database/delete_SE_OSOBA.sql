create or replace procedure delete_SE_OSOBA(
  rc_mazanej_osoby SE_osoba.rod_cislo%TYPE
)
is 

begin
for id_zamestnancov in (select id_zamestnanca from SE_ZAMESTNANEC where rod_cislo = rc_mazanej_osoby) loop
  delete_SE_ZAMESTNANEC(id_zamestnancov.id_zamestnanca);
end loop;

for cisla_odberatelov in (select cislo_odberatela from SE_ODBERATEL where rod_cislo = rc_mazanej_osoby) loop
  delete_SE_ODBERATEL(cisla_odberatelov.cislo_odberatela);
end loop;


delete from SE_OSOBA where rod_cislo = rc_mazanej_osoby;
commit;
end;
/