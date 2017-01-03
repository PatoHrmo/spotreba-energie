create or replace procedure delete_SE_FIRMA(
  ico_mazanej_firmy SE_firma.ico%TYPE
)
is 

begin
for cisla_odberatelov in (select cislo_odberatela from SE_ODBERATEL where ico = ico_mazanej_firmy) loop
  delete_SE_ODBERATEL(cisla_odberatelov.cislo_odberatela);
end loop;
delete from SE_FIRMA where ico = ico_mazanej_firmy;
commit;
end;
/