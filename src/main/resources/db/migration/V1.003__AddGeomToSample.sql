alter table samples add pos geometry;


update samples set pos = ST_GeomFromEWKT('SRID=3857;POINT(11.059909 60.792975)') where sample_id = 1;
update samples set pos = ST_GeomFromEWKT('SRID=3857;POINT(11.069909 60.792975)') where sample_id = 2;
update samples set pos = ST_GeomFromEWKT('SRID=3857;POINT(11.079909 60.792975)') where sample_id = 3;
update samples set pos = ST_GeomFromEWKT('SRID=3857;POINT(11.089909 60.792975)') where sample_id = 4;
