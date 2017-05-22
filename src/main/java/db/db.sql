drop user if exists ds;
create user ds with password 'ds' ;
ALTER USER ds WITH PASSWORD 'ds';

create database ds_dev with encoding='utf8' ;
create database ds_prod with encoding='utf8' ;
create database ds_test with encoding='utf8' ;

grant all privileges on database ds_dev to ds ;
grant all privileges on database ds_test to ds;
grant all privileges on database ds_prod to ds;

\connect ds_dev;
create schema extensions;
create extension hstore schema extensions;
ALTER DATABASE ds_dev SET search_path to "$user",public,extensions;
alter database ds_dev owner to ds;
alter schema public owner to ds;
alter schema extensions owner to ds;
GRANT USAGE ON SCHEMA public to ds;

\connect ds_prod;
create schema extensions;
create extension hstore schema extensions;
ALTER DATABASE ds_prod SET search_path to "$user",public,extensions;
alter database ds_prod owner to ds;
alter schema public owner to ds;
alter schema extensions owner to ds;
grant usage on schema public to ds;

\connect ds_test;
create schema extensions;
create extension hstore schema extensions;
ALTER DATABASE ds_test SET search_path to "$user",public,extensions;
alter database ds_test owner to ds;
alter schema public owner to ds;
alter schema extensions owner to ds;
grant usage on schema public to ds;
