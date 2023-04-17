create table address (
    id serial primary key not null,
    country varchar,
    city varchar,
	street varchar,
	house varchar
);

insert into address (country, city, street, house) values ('country1', 'city1', 'street1', '1');
insert into address (country, city, street, house) values ('country2', 'city2', 'street2', '2');
insert into address (country, city, street, house) values ('country3', 'city3', 'street3', '3');

create table person (
    id serial primary key not null,
    login varchar(2000) unique,
    password varchar(2000)
);

insert into person (login, password) values ('parsentev', '123');
insert into person (login, password) values ('ban', '123');
insert into person (login, password) values ('ivan', '123');
