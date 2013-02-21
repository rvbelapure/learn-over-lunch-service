-- execute everything after this as 'learn' user in mysql
-- creating tables

create database loldb;
use loldb;

create table users_mst
(uname varchar(20) primary key,
fname varchar(30) not null,
lname varchar(30) not null,
passwd varchar(20) not null,
dob varchar(20) not null,
email varchar(50) not null,
phone varchar(20) not null,
edu varchar(20) not null,
work varchar(20) not null,
rating float not null);

create table categories_mst
(cat_id integer primary key auto_increment,
cat_name varchar(20) not null);

create table friends_mst
(initiator varchar(20) not null,
acceptor varchar(20) not null,
status varchar(10) not null,
FOREIGN KEY(initiator) references users_mst(uname),
FOREIGN KEY(acceptor) references users_mst(uname));

create table restaurants_mst
(rest_name varchar(50) primary key,
event_count integer not null,
rating float not null);

create table events_mst
(event_id integer primary key auto_increment,
event_date timestamp not null,
event_place varchar(50) not null,
topic_name varchar(100) not null,
topic_category varchar(20) not null,
max_allowed_members integer not null,
event_members varchar(1000) not null);

create table notification_mst
(noti_id integer primary key auto_increment,
noti_type varchar(20) not null,
frnd_name varchar(60),
frnd_msg varchar(1000),
event_date varchar(50),
event_place varchar(50),
event_topic varchar(100));
