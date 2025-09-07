delete from Book;
alter table Book alter column id restart with 1;
insert into Book(id, title, author, publish_year) values (1, 'title1', 'author1', '2001');
alter table Book alter column id restart with 2;
