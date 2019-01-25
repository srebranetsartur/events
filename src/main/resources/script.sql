-- CREATE SEQUENCE event_id
-- -- start with 100;
-- --
-- -- create table EVENTS
-- -- (
-- --   ID bigint default event_id.nextval primary key,
-- --   TITLE       VARCHAR(200),
-- --   TYPE        VARCHAR(50),
-- --   MEMBERS      ARRAY not null,
-- --   DESCRIPTION CLOB,
-- --   STARTDATE   TIMESTAMP,
-- --   LOCATION    VARCHAR(200)
-- -- );


INSERT INTO EVENTS (TITLE, TYPE, MEMBERS, DESCRIPTION, STARTDATE, LOCATION) VALUES
  ('Concert1', 'Concert', ('1', '2', '3'), 'qqqq', current_date, 'Kiev'),
  ('Concert2', 'Concert', ('Paul', 'Nick'), 'qqqq', dateadd('day', 2, current_date), 'Kiev'),
  ('Interpol Ann', 'Concert',
   ('Interpol'), 'Interpol 10-years anniversary concert', {ts '2019-02-10 19:00:00.00'},
   'Kiev');

select *
from EVENTS;


delete from EVENTS;