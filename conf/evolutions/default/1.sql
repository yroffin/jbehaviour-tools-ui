# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table default_model (
  id                        bigint not null,
  lastUpdate                timestamp not null,
  constraint pk_default_model primary key (id))
;

create table local_story (
  id                        bigint not null,
  lastUpdate                timestamp not null,
  name                      varchar(64) not null,
  description               varchar(1024) not null,
  story                     varchar(65536) not null,
  constraint pk_local_story primary key (id))
;

create sequence default_model_seq;

create sequence local_story_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists default_model;

drop table if exists local_story;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists default_model_seq;

drop sequence if exists local_story_seq;

