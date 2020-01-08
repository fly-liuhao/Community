-- 发布的问题
create table question
(
	id int auto_increment,
	title varchar(256) not null,
	description text,
	gmt_create bigint not null,
	gmt_modify bigint not null,
	creator int not null,
	comment_count int default 0,
	view_count int default 0,
	like_count int default 0,
	tag varchar(256),
	constraint question_pk
		primary key (id)
);

