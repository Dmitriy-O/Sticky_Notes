create table user_entity
(
    id       serial primary key,
    name     varchar,
    password varchar,
    email    varchar
);
create table role_entity
(
    id   serial primary key,
    role varchar
);

create table users_roles
(
    user_id int,
    role_id int
);
alter table users_roles
    add constraint "user_id_foreign_key" foreign key (user_id) references user_entity (id);

alter table users_roles
    add constraint "role_id_foreign_key" foreign key (role_id) references role_entity (id);