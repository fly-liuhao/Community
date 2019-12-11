create table USER
(
    ID         INT auto_increment,
    ACCOUNT_ID VARCHAR(100) not null,
    NAME       VARCHAR(20)  not null,
    TOKEN      CHAR(36)     not null,
    GMT_CREATE BIGINT       not null,
    GMT_MODIFY BIGINT       not null,
    constraint USER_PK
        primary key (ID)
);

