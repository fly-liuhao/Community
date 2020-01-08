create table user
(
    id         int auto_increment,
    account_id varchar(100) not null,
    name       varchar(20)  not null,
    token      char(36)     not null,
    gmt_create bigint       not null,
    gmt_modify bigint       not null,
    constraint user_pk
        primary key (id)
);

