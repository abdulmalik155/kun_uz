select * from article;
select * from profile;
select * from profile_role;
update article set visible = true where id = '42963aef-7e89-4243-ab35-e50b90434b2b';
insert into profile_role (profile_id, role) values (172, 'ROLE_PUBLISHER');