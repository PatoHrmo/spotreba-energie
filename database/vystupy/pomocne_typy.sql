-- tvorba pomocneho objektu uchovavajuceho obdobie a velkost spotreby v tomto obdobz
create or replace type zaznam_o_spotrebe_t is object(
  datum_od date,
  datum_do date,
  spotreba number
);
/
-- tvorba pomocnej nested table
create or replace type zaznamy_o_spotrebe_t is table of zaznam_o_spotrebe_t;
/