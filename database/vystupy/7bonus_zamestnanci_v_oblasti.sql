-- najlahsi vystup,  saci zmenit id regionu
select id_zamestnanca, meno, priezvisko, foto from SE_OSOBA join SE_ZAMESTNANEC using(rod_cislo)
where id_regionu =1;