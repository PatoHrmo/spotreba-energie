-- pred spustenim treba skompilovat get_pocet_vymen_zariadenia.sql
select cislo_odberatela, meno||' '||priezvisko meno, meracia_velicina, get_spotreba_za_obdobie(cislo_odberatela,ADD_MONTHS(sysdate,-12),sysdate,meracia_velicina) spotreba
from SE_OSOBA join SE_ODBERATEL odberatel on(odberatel.rod_cislo = SE_OSOBA.ROD_CISLO) join SE_HISTORIA using (cislo_odberatela) 
join SE_ZARIADENIE using(cis_zariadenia) join SE_TYP_ZARIADENIA zariadenie on(zariadenie.typ = SE_ZARIADENIE.TYP)
where get_pocet_vymen_zariadenia(cislo_odberatela,ADD_MONTHS(sysdate,-12),sysdate,zariadenie.MERACIA_VELICINA)>2
group by cislo_odberatela, SE_OSOBA.rod_cislo, meno, priezvisko,meracia_velicina, get_spotreba_za_obdobie(cislo_odberatela,ADD_MONTHS(sysdate,-12),sysdate,meracia_velicina);

