--liquibase formatted sql
--changeset heckfy88:0
--comment: инициализация схемы и расширений

create schema if not exists otp;
create extension if not exists "uuid-ossp";