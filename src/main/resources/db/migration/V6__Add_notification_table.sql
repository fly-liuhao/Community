-- 创建消息通知表
create table notification
(
    id bigint auto_increment, -- 通知id
    notifier int not null,    -- 通知者
    receiver int not null,    -- 接收者
    outer_id int not null,  -- 回复问题或者评论的id
    type int not null,        -- 回复类型（问题还是评论）
    gmt_create bigint not null,    -- 通知时的时间戳
    status int default 0 not null, -- 通知状态（已读和未读）
	constraint notification_pk
		primary key (id)
);
