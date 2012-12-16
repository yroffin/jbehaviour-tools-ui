# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table default_model (
  id                        bigint not null,
  lastUpdate                timestamp not null,
  constraint pk_default_model primary key (id))
;

create table object_entity (
  id                        bigint not null,
  lastUpdate                timestamp not null,
  name                      varchar(64) not null,
  description               varchar(1024) not null,
  father_id                 bigint,
  value_id                  bigint,
  constraint pk_object_entity primary key (id))
;

create table object_field (
  id                        bigint not null,
  lastUpdate                timestamp not null,
  name                      varchar(64) not null,
  description               varchar(1024) not null,
  type                      varchar(16) not null,
  size                      bigint(16) not null,
  constraint pk_object_field primary key (id))
;

create table object_field_value (
  id                        bigint not null,
  lastUpdate                timestamp not null,
  value                     varchar(1024) not null,
  field_id                  bigint,
  entity_id                 bigint,
  constraint pk_object_field_value primary key (id))
;

create table scenario (
  id                        bigint not null,
  lastUpdate                timestamp not null,
  name                      varchar(64) not null,
  description               varchar(1024) not null,
  story                     varchar(65536) not null,
  constraint pk_scenario primary key (id))
;

create sequence default_model_seq;

create sequence object_entity_seq;

create sequence object_field_seq;

create sequence object_field_value_seq;

create sequence scenario_seq;

alter table object_entity add constraint fk_object_entity_father_1 foreign key (father_id) references object_entity (id) on delete restrict on update restrict;
create index ix_object_entity_father_1 on object_entity (father_id);
alter table object_entity add constraint fk_object_entity_value_2 foreign key (value_id) references object_field_value (id) on delete restrict on update restrict;
create index ix_object_entity_value_2 on object_entity (value_id);
alter table object_field_value add constraint fk_object_field_value_field_3 foreign key (field_id) references object_field (id) on delete restrict on update restrict;
create index ix_object_field_value_field_3 on object_field_value (field_id);
alter table object_field_value add constraint fk_object_field_value_entity_4 foreign key (entity_id) references object_entity (id) on delete restrict on update restrict;
create index ix_object_field_value_entity_4 on object_field_value (entity_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists default_model;

drop table if exists object_entity;

drop table if exists object_field;

drop table if exists object_field_value;

drop table if exists scenario;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists default_model_seq;

drop sequence if exists object_entity_seq;

drop sequence if exists object_field_seq;

drop sequence if exists object_field_value_seq;

drop sequence if exists scenario_seq;

