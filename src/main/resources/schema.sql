CREATE SEQUENCE USER_SEQ;

CREATE TABLE USERS (
	id int PRIMARY KEY AUTO_INCREMENT,
	username VARCHAR(50) NOT NULL,
	password VARCHAR(100) NOT NULL,
	email VARCHAR(100) NOT NULL
);