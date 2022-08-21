create table if not exists notifications (
    id varchar(64) primary key,
    user_id varchar(64) not null,
    content varchar(512) not null,
    flag varchar(32) not null
)
