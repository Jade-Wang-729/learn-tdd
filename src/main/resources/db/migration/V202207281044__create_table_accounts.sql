create table if not exists accounts(
    id varchar(64) primary key,
    username varchar(64) not null unique,
    password varchar(128) not null
);
