insert into user_entity(name,password,email)
values ('Mike','$2a$12$tnbc7il.9rJF/Gmn.nDRTe85N21nKhbzIY6yn.dn4ytBFkuVca8mC',
'mike@gmail.com');
insert into user_entity(name,password,email)
values ('Lisa','$2a$12$AtmFu1qqR2T0leScXva2MeLJaf5TLidT81sN0TvVFEfmGsE6hF8B6',
        'lisa@gmail.com');
insert into role_entity(role)
values ('ROLE_USER');
insert into role_entity(role)
values ('ROLE_ADMIN');
insert into users_roles(role_id, user_id)
values (1,1);
insert into users_roles(role_id, user_id)
values (2,2)