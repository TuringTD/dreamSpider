CREATE SEQUENCE hibernate_sequence;

CREATE TABLE article
(
  id BIGSERIAL NOT NULL PRIMARY KEY,
  title character varying(255),
  content TEXT ,
  created timestamp without time zone,
  url character varying(255)
);