--liquibase formatted sql
--changeset heckfy88:2
--comment: добавлены таблицы для otp

create table otp.config
(
    id         int       not null default 1,
    length     int       not null,
    duration   int       not null,
    updated_at timestamp not null default now(),

    constraint pk_otp_config primary key (id),
    constraint length_constraint check (length >= 4 and length <= 32),
    constraint single_otp_config_row check (id = 1)
);

-- предзаполнение
insert into otp.config (id, length, duration)
values (1,  4, 0);

comment on table otp.config is 'Таблица с настройками для otp';

comment on column otp.config.id is 'Ограничение для одной строки';
comment on column otp.config.length is 'Длина токена';
comment on column otp.config.duration is 'Время жизни токена в секундах';
comment on column otp.config.updated_at is 'Время последнего обновления';


create table otp.token
(
    id           uuid      not null default uuid_generate_v4(),
    user_id      uuid      not null,
    operation_id uuid      not null,
    code         varchar   not null,
    status       varchar   not null,
    created_at   timestamp not null default now(),
    updated_at   timestamp not null default now(),
    expires_at   timestamp not null,

    constraint pk_otp_token primary key (id),
    constraint status_constraint check (status in ('ACTIVE', 'EXPIRED', 'USED')),
    constraint fk_otp_token_user foreign key (user_id) references otp.user (id)
);

comment on table otp.token is 'Таблица для хранения токенов';

comment on column otp.token.id is 'Уникальный идентификатор токена';
comment on column otp.token.user_id is 'Идентификатор пользователя';
comment on column otp.token.operation_id is 'Идентификатор операции';
comment on column otp.token.code is 'Токен';
comment on column otp.token.status is 'Статус токена';
comment on column otp.token.created_at is 'Время создания токена';
comment on column otp.token.updated_at is 'Время обновления токена';
comment on column otp.token.expires_at is 'Время жизни токена';