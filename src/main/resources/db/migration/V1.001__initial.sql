SET client_encoding='UTF8';

CREATE TABLE sample(
  sample_id BIGSERIAL PRIMARY KEY,
  description VARCHAR(255),
  pos geometry
);
