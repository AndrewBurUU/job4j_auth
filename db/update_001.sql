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
    login varchar(2000),
    password varchar(2000)
    address_id int references address(id)
);

insert into person (login, password, address_id) values ('parsentev', '123', 1);
insert into person (login, password, address_id) values ('ban', '123', 1);
insert into person (login, password, address_id) values ('ivan', '123', 1);
