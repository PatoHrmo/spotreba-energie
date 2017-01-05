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