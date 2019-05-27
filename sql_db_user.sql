CREATE DATABASE kaicalib_db;
CREATE DATABASE kaicalib_accounts_db;

CREATE USER 'kaicalib_db_user'@'localhost' IDENTIFIED BY 'enter112';

GRANT ALL ON kaicalib_db.* TO 'kaicalib_db_user'@'localhost';
GRANT ALL ON kaicalib_accounts_db.* TO 'kaicalib_db_user'@'localhost';


-- ALTER USER 'bibsys_db_user'@'localhost' IDENTIFIED BY 'enter112';
