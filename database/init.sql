alter user 'root'@'localhost' identified with caching_sha2_password by 'root';
flush privileges;

create database if not exists project2;

use project2;