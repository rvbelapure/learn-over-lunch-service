-- execute everything after this as 'learn' user in mysql
-- creating tables

create database loldb;
use loldb;

create table users_mst
(uname varchar(20) primary key,
fname varchar(30) not null,
lname varchar(30) not null,
passwd varchar(20) not null,
dob varchar(20),
email varchar(50),
phone varchar(20),
edu varchar(20),
work varchar(20),
rating float not null);

create table categories_mst
(cat_id integer primary key auto_increment,
cat_name varchar(20) not null);

create table restaurants_mst
(rest_name varchar(50) primary key,
rest_city varchar(50) not null,
event_count integer not null,
rating float not null);

create table events_mst
(event_id integer primary key auto_increment,
event_date timestamp not null,
event_place varchar(50) not null,
topic_name varchar(100) not null,
topic_category varchar(20) not null,
topic_desc varchar(200),
max_allowed_members integer not null);

create table event_attendees
(event integer not null,
event_members varchar(20) not null,
FOREIGN KEY(event) references events_mst(event_id),
FOREIGN KEY(event_members) references users_mst(uname));

create table notification_mst
(noti_id integer primary key auto_increment,
noti_type varchar(20) not null,
frnd_name varchar(60),
frnd_msg varchar(1000),
event_date varchar(50),
event_place varchar(50),
event_topic varchar(100));
