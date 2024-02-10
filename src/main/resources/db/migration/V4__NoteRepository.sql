create table If Not Exists note_entity(
    id serial primary key ,
    title varchar,
    author_name varchar,
    author_surname varchar,
    author_role varchar,
    description varchar(10000),
    date_issue bigint,
    date_update bigint,
    is_active boolean
)