-- najlahsi vystup,  saci zmenit id regionu
select meno, priezvisko from SE_OSOBA join SE_ZAMESTNANEC using(rod_cislo)
where id_regionu =1;