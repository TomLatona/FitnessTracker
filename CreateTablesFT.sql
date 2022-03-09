use fitnesstrackerdb;

create table users(
username varchar(15),
password varchar(20),
caloriegoal varchar (5),
userId varchar(10) primary key
);

create table usermeals(
fooditem varchar(30),
calories int,
userID varchar(10) references users(userID)
);


