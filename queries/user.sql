-- creating new user	- run this as root user in mysql
CREATE USER 'learn'@'localhost' IDENTIFIED BY 'learn';
GRANT ALL PRIVILEGES ON * . * TO 'learn'@'localhost';
FLUSH PRIVILEGES;
