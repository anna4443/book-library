create table if not exists users(
    id identity,
    name varchar(100) not null,
    surname varchar(100) not null,
    username varchar(100) not null,
    email varchar(100) not null,
    password varchar(500) not null,
    enabled bit not null
);

create table if not exists authorities (
  username varchar(100) not null,
  authority varchar(20) not null,
  constraint fk_authorities_user foreign key(username) references users(username)
);

create table if not exists book(
    id identity,
    title varchar(500) not null,
    author varchar(500) not null,
    year_release number not null,
    img varchar(1000) not null
);

create table if not exists loan(
    id identity,
    name varchar(100) not null,
    surname varchar(100) not null,
    username varchar(100) not null,
    title varchar(500) not null,

    constraint fk_loan_user_name foreign key(name) references users(name),
    constraint fk_loan_user_surname foreign key(surname) references users(surname),
    constraint fk_loan_user foreign key(username) references users(username),
    constraint fk_loan_title foreign key(title) references book(title)
);