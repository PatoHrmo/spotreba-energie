-- mozne je zmenit len najnovsi odpis na zariadeni
-- ak menime aj zamestnanca ktory vykonal odpis - novy musi byt z toho isteho regionu
create or replace procedure update_SE_ODPIS (
    datum_meneneho_odpisu SE_ODPIS.datum_odpisu%TYPE,
    cislo_zariadenia_odpisu SE_ODPIS.cis_zariadenia%TYPE,
    id_noveho_zamestnanca SE_ODPIS.id_zamestnanca%TYPE,
    novy_stav SE_ODPIS.stav%TYPE
)
is
  datum_posledneho_odpisu SE_ODPIS.datum_odpisu%TYPE;
  zmena_stareho_odpisu EXCEPTION;
  id_regionu_stareho_zam SE_REGION.id_regionu%TYPE;
  id_regionu_noveho_zam SE_REGION.id_regionu%TYPE;
  zamestnanec_z_ineho_regionu EXCEPTION;
  id_stareho_zamestnanca SE_ZAMESTNANEC.id_zamestnanca%TYPE;
begin
    -- menit mozeme len najnovsi odpis
   select max(datum_odpisu) into datum_posledneho_odpisu
   from SE_ODPIS 
   where cis_zariadenia = cislo_zariadenia_odpisu;
   
   IF datum_posledneho_odpisu <> datum_meneneho_odpisu THEN
    raise zmena_stareho_odpisu;
   end if;
   -- zistovanie id zamestnanca ktory je aktualne pri tomto odpise
   select id_zamestnanca into id_stareho_zamestnanca 
   from SE_ODPIS
   where cis_zariadenia = cislo_zariadenia_odpisu
   and datum_odpisu = datum_posledneho_odpisu;
   -- ak sme nezmenili id_zamestnanca nemusime kontrolovat ci je z ineho regionu
   IF id_noveho_zamestnanca <> id_stareho_zamestnanca  then
   
      select id_regionu into id_regionu_stareho_zam 
      from SE_ZAMESTNANEC 
      where id_zamestnanca = id_stareho_zamestnanca;
      
      select id_regionu into id_regionu_noveho_zam
      from SE_ZAMESTNANEC 
      where id_zamestnanca = id_noveho_zamestnanca;
      
      --kontrola ci su z rovnakeho id_regionu
      IF id_regionu_noveho_zam <> id_regionu_stareho_zam then
         RAISE zamestnanec_z_ineho_regionu;
      end if;
   end if;
   
   UPDATE SE_ODPIS 
   SET stav = novy_stav,
   id_zamestnanca = id_noveho_zamestnanca
   WHERE cis_zariadenia = cislo_zariadenia_odpisu
   and datum_odpisu = datum_meneneho_odpisu;
   
   UPDATE SE_ZARIADENIE
   set spotreba = novy_stav
   where cis_zariadenia = cislo_zariadenia_odpisu;
   
   commit;
   EXCEPTION
    WHEN zmena_stareho_odpisu THEN
        RAISE_APPLICATION_ERROR(-20001,'pokus o zmenu stareho odpisu');
    WHEN zamestnanec_z_ineho_regionu THEN
        RAISE_APPLICATION_ERROR(-20002,'novy zamestnanec je z ineho regionu');
   
end;
/









