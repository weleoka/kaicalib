CREATE DATABASE bibsys_db;
CREATE DATABASE bibsys_accounts_db;

CREATE USER 'bibsys_db_user'@'localhost' IDENTIFIED BY 'enter112';

GRANT ALL ON bibsys_db.* TO 'bibsys_db_user'@'localhost';
GRANT ALL ON bibsys_accounts_db.* TO 'bibsys_db_user'@'localhost';


ALTER USER 'bibsys_db_user'@'localhost' IDENTIFIED BY 'enter112';
