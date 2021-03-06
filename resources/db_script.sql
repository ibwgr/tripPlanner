-----------------------------------------------------------
-- Drop tables if already exists !
-----------------------------------------------------------
DROP VIEW IF EXISTS tp_trip_full_v, tp_trip_aggr_v;
DROP TABLE IF EXISTS poi, poi_category, tp_user, tp_activity, tp_trip;

-----------------------------------------------------------
-- Create tables
-----------------------------------------------------------

CREATE TABLE poi_category
(
  id     varchar (50) PRIMARY KEY,
  name   varchar (1000)
);

CREATE TABLE poi
(
  id            varchar (50) PRIMARY KEY,
  longitude     varchar (20),
  latitude      varchar (20),
  category_id   varchar (50),
  name          varchar (1000),
  FOREIGN KEY (category_id) REFERENCES poi_category (id)
);

CREATE SEQUENCE tp_user_id_seq;

CREATE TABLE tp_user
(
  id         int PRIMARY KEY NOT NULL DEFAULT nextval ('tp_user_id_seq'),
  username   varchar (100),
  password   varchar (100),
  email      varchar (100),
  name       varchar (250),
  type       int                               -- 1 = User, 2 = Administrator
);

ALTER SEQUENCE tp_user_id_seq OWNED BY tp_user.id;


CREATE SEQUENCE tp_trip_id_seq;

CREATE TABLE tp_trip
(
  id        int PRIMARY KEY NOT NULL DEFAULT nextval ('tp_trip_id_seq'),
  user_id   int,
  name      varchar (250),
  FOREIGN KEY (user_id) REFERENCES tp_user (id)
);

ALTER SEQUENCE tp_trip_id_seq OWNED BY tp_trip.id;


CREATE SEQUENCE tp_activity_id_seq;

CREATE TABLE tp_activity
(
  id        int PRIMARY KEY NOT NULL DEFAULT nextval ('tp_activity_id_seq'),
  trip_id   int,
  poi_id    varchar (50),
  date      date,
  comment   varchar (250),
  city      varchar (250),
  FOREIGN KEY (trip_id) REFERENCES tp_trip (id) ON DELETE CASCADE,
  FOREIGN KEY (poi_id) REFERENCES poi (id)
);

ALTER SEQUENCE tp_activity_id_seq OWNED BY tp_activity.id;

--alter table tp_activity add city varchar(250)


-----------------------------------------------------------
-- Function for update when id already exists
-----------------------------------------------------------


CREATE OR REPLACE FUNCTION poi_insert_before_func()
  RETURNS trigger AS

$BODY$

DECLARE
  exists VARCHAR;
BEGIN

  UPDATE poi SET longitude=new.longitude, latitude=new.latitude, category_id=new.category_id, name=new.name
  WHERE id=new.id
  RETURNING id INTO exists;

  -- If the above was successful, it would return non-null
  -- in that case we return NULL so that the triggered INSERT
  -- does not proceed
  IF exists is not null THEN
    RETURN NULL;
  END IF;

  -- Otherwise, return the new record so that triggered INSERT
  -- goes ahead
  RETURN new;


END;

$BODY$

LANGUAGE plpgsql VOLATILE
COST 100;

CREATE OR REPLACE FUNCTION poi_category_insert_before_func()
  RETURNS trigger AS

$BODY$

DECLARE
  exists VARCHAR;
BEGIN

  UPDATE poi_category SET name=new.name
  WHERE id=new.id
  RETURNING id INTO exists;

  -- If the above was successful, it would return non-null
  -- in that case we return NULL so that the triggered INSERT
  -- does not proceed
  IF exists is not null THEN
    RETURN NULL;
  END IF;

  -- Otherwise, return the new record so that triggered INSERT
  -- goes ahead
  RETURN new;


END;

$BODY$

LANGUAGE plpgsql VOLATILE
COST 100;


-----------------------------------------------------------
-- Trigger for insert into poi table
-----------------------------------------------------------

CREATE TRIGGER poi_insert_before_trigger
BEFORE INSERT
  ON public.poi
FOR EACH ROW
EXECUTE PROCEDURE poi_insert_before_func ();

CREATE TRIGGER poi_category_insert_before_trigger
BEFORE INSERT
  ON public.poi_category
FOR EACH ROW
EXECUTE PROCEDURE poi_category_insert_before_func ();


-----------------------------------------------------------
-- VIEW: Reise (Komplett inkl. Aktivitaeten und POI)
-----------------------------------------------------------
DROP VIEW IF EXISTS tp_trip_aggr_v;
DROP VIEW IF EXISTS tp_trip_full_v;

CREATE OR REPLACE VIEW tp_trip_full_v
AS
  SELECT t.id AS trip_id,
    t.user_id,
    t.name  AS trip_name,
    a.id    AS activity_id,
    a.date,
    a.comment,
    a.city,
    a.poi_id,
    p.longitude,
    p.latitude,
    p.name  AS poi_name,
    p.category_id,
    pc.name AS poi_category_name
  FROM tp_trip t
    LEFT JOIN tp_activity a ON t.id = a.trip_id
    LEFT JOIN poi p ON a.poi_id = p.id
    LEFT JOIN poi_category pc ON p.category_id = pc.id;

/*
select *
from tp_trip_full_v
;
*/
-- VIEW: Reise (inkl. aggregierte Aktivitaets-Informationen)

CREATE OR REPLACE VIEW tp_trip_aggr_v
AS
  SELECT trip_id,
    user_id,
    trip_name,
    max (date)        AS max_date,
    min (date)        AS min_date,
    count (activity_id) AS count_acitvities
  FROM tp_trip_full_v
  GROUP BY trip_id, user_id, trip_name;

/*
select trip_id, user_id, trip_name, max_date, min_date, count_acitvities
from tp_trip_aggr_v
;
*/


-----------------------------------------------------------
-- some dummy USER data
-----------------------------------------------------------
INSERT INTO tp_user (username,
                     password,
                     email,
                     name,
                     type)                   /* 1 = User, 2 = Administrator */
VALUES ('benutzer',
        md5('benutzer'),
        'benutzer@example.com',
        'benutzer',
        1);

INSERT INTO tp_user (username,
                     password,
                     email,
                     name,
                     type)                   /* 1 = User, 2 = Administrator */
VALUES ('admin',
        md5('admin'),
        'admin@example.com',
        'admin',
        2);

COMMIT;
/*
select *
from tp_user
;
*/

-----------------------------------------------------------
-- some dummy POI_CATEGORY data
-----------------------------------------------------------
/*
select 'insert into poi_category (id, name) values (''' ||id ||''',''' ||name ||''');'
from poi_category
where id in (
	select category_id from poi
	where id in (
	'N444868954',
	'N1680808493',
	'N27377535',
	'W438952439',
	'N1599473536',
	'W244384754',
	'W36194480',
	'N4389197835'
	)
)
*/
insert into poi_category (id, name) values ('6','ACCOMMO_HOTEL');
insert into poi_category (id, name) values ('25','FOOD_CAFE');
insert into poi_category (id, name) values ('40','LANDUSE_GRASS');
insert into poi_category (id, name) values ('66','POI_CITY');
insert into poi_category (id, name) values ('69','POI_TOWN');
insert into poi_category (id, name) values ('164','TRANSPORT_MARINA');
commit;
/*
select * from poi_category
*/
-----------------------------------------------------------
-- some dummy POI data
-----------------------------------------------------------
/*
select 'insert into poi (id, longitude, latitude, category_id, name) values (''' ||id ||''',' ||longitude ||',' ||latitude ||',' ||category_id ||',''' ||name ||''');'
from poi p
where id in (
'N444868954',
'N1680808493',
'N27377535',
'W438952439',
'N1599473536',
'W244384754',
'W36194480',
'N4389197835'
)
 */
insert into poi (id, longitude, latitude, category_id, name) values ('N1599473536',15.5866422,56.1621073,66,'Karlskrona');
insert into poi (id, longitude, latitude, category_id, name) values ('N1680808493',13.8210691,55.4302902,25,'Kaffestugan Bäckahästen');
insert into poi (id, longitude, latitude, category_id, name) values ('N27377535',14.1566859,56.0293778,69,'Kristianstad');
insert into poi (id, longitude, latitude, category_id, name) values ('N4389197835',16.4672577,56.6488992,6,'Hotell Skansen');
insert into poi (id, longitude, latitude, category_id, name) values ('N444868954',12.9989894,55.6057336,6,'Scandic Stortorget');
insert into poi (id, longitude, latitude, category_id, name) values ('W244384754',16.0446385,56.2566705,164,'Kristianopel hamnen');
insert into poi (id, longitude, latitude, category_id, name) values ('W36194480',16.3585569,56.6595944,40,'Sylvanderparken');
insert into poi (id, longitude, latitude, category_id, name) values ('W438952439',14.8722605,56.1660065,40,'Väggaparken');
commit;
/*
select * from poi
*/
-----------------------------------------------------------
-- some dummy TRIP data
-----------------------------------------------------------
INSERT INTO tp_trip (user_id, name) VALUES (1, 'Sommerferien Schweden 2016');
INSERT INTO tp_trip (user_id, name) VALUES (1, 'Frühlingsferien USA 2017 mit Kunzes aus Bünzen');
COMMIT;
/*
select *
from tp_trip;
*/
-----------------------------------------------------------
-- some dummy TR_ACTIVITY data --> load sweden-latest.csv first!
-----------------------------------------------------------
INSERT INTO tp_activity (trip_id, poi_id, date, comment, city) VALUES ( (select id from tp_trip where name = 'Sommerferien Schweden 2016'), 'N444868954',  to_date('2017-01-01','yyyy-mm-dd'), '1 Malmö Flughafen', 'Malmö');
INSERT INTO tp_activity (trip_id, poi_id, date, comment, city) VALUES ( (select id from tp_trip where name = 'Sommerferien Schweden 2016'), 'N1680808493', to_date('2017-01-02','yyyy-mm-dd'), '2 Kommissar Wallander Rundfahrt', 'Ystad');
INSERT INTO tp_activity (trip_id, poi_id, date, comment, city) VALUES ( (select id from tp_trip where name = 'Sommerferien Schweden 2016'), 'N27377535',   to_date('2017-01-03','yyyy-mm-dd'), '3 nur Zwischenstop bei Onkel Björn', 'Kristianstad');
INSERT INTO tp_activity (trip_id, poi_id, date, comment, city) VALUES ( (select id from tp_trip where name = 'Sommerferien Schweden 2016'), 'W438952439',  to_date('2017-01-04','yyyy-mm-dd'), '4 schöner Park', 'Karlshamn');
INSERT INTO tp_activity (trip_id, poi_id, date, comment, city) VALUES ( (select id from tp_trip where name = 'Sommerferien Schweden 2016'), 'N1599473536', to_date('2017-01-05','yyyy-mm-dd'), '5 Schärengarten Rundfahrt mit dem Schiff', 'Karlskrona');
INSERT INTO tp_activity (trip_id, poi_id, date, comment, city) VALUES ( (select id from tp_trip where name = 'Sommerferien Schweden 2016'), 'W244384754',  to_date('2017-01-06','yyyy-mm-dd'), '6 Der kleine Hafen ist grossartig', 'Kristianopel');
INSERT INTO tp_activity (trip_id, poi_id, date, comment, city) VALUES ( (select id from tp_trip where name = 'Sommerferien Schweden 2016'), 'W36194480',   to_date('2017-01-07','yyyy-mm-dd'), '7 Schöner Park', 'Kalmar');
INSERT INTO tp_activity (trip_id, poi_id, date, comment, city) VALUES ( (select id from tp_trip where name = 'Sommerferien Schweden 2016'), 'N4389197835', to_date('2017-01-08','yyyy-mm-dd'), '8 Übernachtung auf Öland', 'Färjestaden');
COMMIT;
/*
select *
from tp_activity
*/