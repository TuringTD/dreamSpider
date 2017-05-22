drop user if exists pds;
create user pds with password 'pds' ;
ALTER USER pds WITH PASSWORD 'pds';

create database ds_dev with encoding='utf8' ;
create database ds_prod with encoding='utf8' ;
create database ds_test with encoding='utf8' ;

grant all privileges on database ds_dev to pds ;
grant all privileges on database ds_test to pds;
grant all privileges on database ds_prod to pds;

\connect ds_dev;
create schema extensions;
create extension hstore schema extensions;
ALTER DATABASE ds_dev SET search_path to "$user",public,extensions;
alter database ds_dev owner to pds;
alter schema public owner to pds;
alter schema extensions owner to pds;
GRANT USAGE ON SCHEMA public to pds;

\connect ds_prod;
create schema extensions;
create extension hstore schema extensions;
ALTER DATABASE ds_prod SET search_path to "$user",public,extensions;
alter database ds_prod owner to pds;
alter schema public owner to pds;
alter schema extensions owner to pds;
grant usage on schema public to pds;

\connect ds_test;
create schema extensions;
create extension hstore schema extensions;
ALTER DATABASE ds_test SET search_path to "$user",public,extensions;
alter database ds_test owner to pds;
alter schema public owner to pds;
alter schema extensions owner to pds;
grant usage on schema public to pds;
