use fitnesstrackerdb;

create table if not exists users(
username varchar(15) not null primary key unique,
password varchar(20) not null,
caloriegoal varchar (5) not null,
userId varchar(10) unique
);

create table if not exists usermeals(
mealId varchar(30) not null,
servings int not null ,
userID varchar(10) references users(userID)
);


create table if not exists meals(
foodItem varchar(30) not null,
caloriesPerServing int not null,
userMealId varchar(30) references usermeals(mealId)
);

create table if not exists exercises(
excerciesId varchar(10) primary key not null ,
name varchar(30) not null ,
reps int not null,
caloriesLostPerAverageReps int not null
);

create table if not exists userExercises(
userId varchar(10) not null primary key,
time int not null,
excercieId varchar(10) not null,
foreign key (excersieId) references exercises(excerciesId)
); 



