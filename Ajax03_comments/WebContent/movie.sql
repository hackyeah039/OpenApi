-- movie.sql
create table movie
(
	mnum number(5) primary key,
	title varchar2(20),
	content varchar2(100),
	director varchar2(50)
);
create table comments
(
	num number(5) primary key,
	mnum number(5) references movie(mnum),
	id varchar2(20),
	comments varchar2(50)
);
create sequence movie_seq;
create sequence comments_seq;