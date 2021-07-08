drop database if exists worldgdp;
create database worldgdp;
drop user if exists 'tobi'@'localhost';
create user 'tobi'@'localhost' identified by 'tobi_123';
grant all privileges on worldgdp.* to 'tobi'@'localhost';
flush privileges;