-- PRED SPUSTENIM TOHTO SELECTU JE POTREBENE MAT SKOMPILOVANY SUBOR get_spotreba_za_obdobe.sql
-- na tento 6. vystup staci select
-- menit sa budu len 3 hodnoty - datum_od datum_do a nazov veliciny, vsetko ostatne ostava
select meno, cislo_odberatela from (select  rank() over (
  order by get_spotreba_za_obdobie(cislo_odberatela,to_date('10.10.2013','dd.mm.yyyy'),to_date('10.10.2015','dd.mm.yyyy'),'voda')) as rn,
  count(*) over() as pocet,
  meno||' '||priezvisko as meno,
  cislo_odberatela
  from SE_ODBERATEL join SE_OSOBA using(rod_cislo)) 
where rn<pocet*0.1;