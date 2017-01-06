-- pomocky na 1. vystup

-- tvorba pomocneho objektu uchovavajuceho obdobie a velkost spotreby v tomto obdoby
create or replace type zaznam_o_spotrebe_t is object(
  datum_od date,
  datum_do date,
  spotreba number
);
/
-- tvorba pomocnej nested table
create or replace type zaznamy_o_spotrebe_t is table of zaznam_o_spotrebe_t;
/

-- pomocky na 2. vystup

create or replace type zvysena_spotreba_t is object (
  meno Varchar2(40),
  velicina Varchar2(20),
  priemerna_spotreba_v_minulosti number,
  zvysena_spotreba number
);
/
create or replace type zaznamy_zvysenej_spotreby_t is table of zvysena_spotreba_t;
/

-- pomocky pre 3 vystup

create or replace type info_poruchovost_t is object (
    cislo_zariadenia integer,
    pocet_servisov_do_5_rokov integer,
    pocet_servisov_typu_0 integer,
    pocet_servisov_typu_1 integer,
    pocet_servisov_typu_2 integer
);
/
create or replace type zaznamy_info_poruchovost_t is table of info_poruchovost_t;
/

-- pomocky pre 4. vystup
create or replace type zaznam_spotreba_energie_t is object (
    typ_odberatela CHAR(1),
    kategoria CHAR(1),
    min_spotreba number,
    mesiac_min_spotreby integer,
    max_spotreba number,
    mesiac_max_spotreby integer,
    priemer number
);
/
create or replace type zaznamy_spotreby_energie_t is table of zaznam_spotreba_energie_t;
/

create or replace type naj_spotreba_t is object (
  mesiac_najvecsia_spotreba date,
  najvacsia_spotreba number,
  mesiac_najmensia_spotreba date,
  najmensia_spotreba number
);
