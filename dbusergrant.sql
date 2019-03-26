CREATE DATABASE fdb_dev;

CREATE USER 'fdb_dev_user'@'localhost' IDENTIFIED BY 'ham22gonG';
CREATE USER 'fdb_dev_user'@'%' IDENTIFIED BY 'ham22gonG';

GRANT SELECT ON fdb_dev.* TO 'fdb_dev_user'@'localhost';
GRANT INSERT ON fdb_dev.* TO 'fdb_dev_user'@'localhost';
GRANT DELETE ON fdb_dev.* TO 'fdb_dev_user'@'localhost';
GRANT UPDATE ON fdb_dev.* TO 'fdb_dev_user'@'localhost';
GRANT SELECT ON fdb_dev.* TO 'fdb_dev_user'@'%';
GRANT INSERT ON fdb_dev.* TO 'fdb_dev_user'@'%';
GRANT DELETE ON fdb_dev.* TO 'fdb_dev_user'@'%';
GRANT UPDATE ON fdb_dev.* TO 'fdb_dev_user'@'%';


CREATE DATABASE fdb_prod;

CREATE USER 'fdb_prod_user'@'localhost' IDENTIFIED BY 'ham22gonG';
CREATE USER 'fdb_prod_user'@'%' IDENTIFIED BY 'ham22gonG';

GRANT SELECT ON fdb_prod.* TO 'fdb_prod_user'@'localhost';
GRANT INSERT ON fdb_prod.* TO 'fdb_prod_user'@'localhost';
GRANT DELETE ON fdb_prod.* TO 'fdb_prod_user'@'localhost';
GRANT UPDATE ON fdb_prod.* TO 'fdb_prod_user'@'localhost';
GRANT SELECT ON fdb_prod.* TO 'fdb_prod_user'@'%';
GRANT INSERT ON fdb_prod.* TO 'fdb_prod_user'@'%';
GRANT DELETE ON fdb_prod.* TO 'fdb_prod_user'@'%';
GRANT UPDATE ON fdb_prod.* TO 'fdb_prod_user'@'%';