-- ユーザー
create table m_user (
    mail_address varchar(256) not null primary key,
    password varchar(256) not null,
    user_name varchar(256) not null,
    permission integer not null
);

comment on table m_user is 'ユーザーマスタ';
comment on column m_user.mail_address is 'メールアドレス';
comment on column m_user.password is 'パスワード';
comment on column m_user.user_name is 'ユーザー名';
comment on column m_user.permission is '権限';