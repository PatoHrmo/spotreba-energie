
CREATE OR REPLACE PROCEDURE insert_SE_ODPIS(
     datum_odpisu IN SE_ODPIS.datum_odpisu%TYPE,
     cislo_zariadenia IN SE_ODPIS.cis_zariadenia%TYPE,
     id_zamestnanca IN SE_ODPIS.id_zamestnanca%TYPE,
     novy_stav IN SE_ODPIS.stav%TYPE
     )
IS
posledny_odpis_stav SE_ODPIS.stav%TYPE;
spotreba_nemoze_klesat EXCEPTION;
odpis_vykonany_v_zlom_case EXCEPTION;
datum_stareho_odpisu date;
BEGIN
  select spotreba into posledny_odpis_stav 
  from SE_ZARIADENIE
  where cis_zariadenia = cislo_zariadenia;
  
  select max(datum_odpisu) into datum_stareho_odpisu
  from SE_odpis
  where cis_zariadenia = cislo_zariadenia;
  
  IF posledny_odpis_stav >  novy_stav THEN
  RAISE spotreba_nemoze_klesat;
  END IF;
  
  IF  datum_stareho_odpisu > datum_odpisu THEN
  RAISE odpis_vykonany_v_zlom_case;
  END IF;
  
  update SE_ZARIADENIE 
  SET spotreba = novy_stav
  where cis_zariadenia = cislo_zariadenia;
  
  INSERT INTO SE_ODPIS VALUES (datum_odpisu, cislo_zariadenia, id_zamestnanca, novy_Stav);

  COMMIT;

END;
/
