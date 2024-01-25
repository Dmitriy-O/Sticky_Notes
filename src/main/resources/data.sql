insert into t_user(id,c_username,c_password)
values (1,'j.jamson','{noop}password');

insert into t_user_authority(id_user,c_authorities)
values (1,'ROLE_MANAGER');