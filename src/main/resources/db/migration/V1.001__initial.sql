CREATE TABLE SAMPLES(
    id INT generated always as IDENTITY PRIMARY KEY,
	sample_id INT not null,
	description varchar(1024),
	point geometry(Point, 4326)
);

