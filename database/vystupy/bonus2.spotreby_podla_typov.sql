select id_regionu, kategoria, meracia_velicina,
sum(get_spotreba_za_obdobie(cislo_odberatela,(select min(datum_odpisu) from SE_ODPIS),(select max(datum_odpisu) from SE_ODPIS),meracia_velicina)) spotreba
from SE_ODPIS join SE_ZARIADENIE using(cis_zariadenia) join SE_TYP_ZARIADENIA using(typ)
join SE_HISTORIA using(cis_zariadenia) join SE_ODBERATEL using (cislo_odberatela) 
join SE_OSOBA using(rod_cislo) join SE_ADRESA using(id_adresy) join SE_MESTO using(id_mesta)
group by id_regionu, kategoria, meracia_velicina
order by id_regionu, kategoria,meracia_velicina;
