--liquibase formatted sql
--changeset heckfy88:1
--comment: добавлена таблица пользователей

create table otp.user
(
    id            uuid                         default uuid_generate_v4(),
    username      varchar(100) unique not null,
    role          varchar(100)        not null,
    email         varchar(100) unique,
    phone_number  varchar(100) unique,
    tg_id         varchar(100) unique,
    password_hash text                not null,
    created_at    timestamp           not null default now(),
    is_active     boolean             not null default true,

    constraint pk_user primary key (id),
    constraint chk_user_role check (role in ('USER', 'ADMIN'))
);

create unique index unique_admin_user on otp.user (role) where role = 'ADMIN';

comment on table otp.user is 'Пользователи';

comment on column otp.user.id is 'Идентификатор пользователя';
comment on column otp.user.username is 'Имя пользователя';
comment on column otp.user.role is 'Роль пользователя';
comment on column otp.user.email is 'Электронная почта пользователя';
comment on column otp.user.phone_number is 'Номер телефона пользователя';
comment on column otp.user.tg_id is 'ID пользователя в Telegram';
comment on column otp.user.password_hash is 'Хэш пароля пользователя';
comment on column otp.user.created_at is 'Дата создания пользователя';
comment on column otp.user.is_active is 'Активность пользователя';