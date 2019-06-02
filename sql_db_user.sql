CREATE DATABASE IF NOT EXISTS kaicalib_db;
CREATE DATABASE IF NOT EXISTS kaicalib_accounts_db;

CREATE USER 'kaicalib_db_user'@'localhost' IDENTIFIED BY 'enter112';

GRANT ALL ON kaicalib_db.* TO 'kaicalib_db_user'@'localhost';
GRANT ALL ON kaicalib_accounts_db.* TO 'kaicalib_db_user'@'localhost';

GRANT ALL ON kaicalib_db.* TO 'kaicalib_db_user'@'%'; -- for TCP
GRANT ALL ON kaicalib_accounts_db.* TO 'kaicalib_db_user'@'%'; -- for TCP

-- ALTER USER 'kaicalib_db_user'@'localhost' IDENTIFIED BY 'enter112';
