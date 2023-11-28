create database if not exists security_db;

CREATE TABLE if not exists roles
(
	`id` 	  varchar(255) not null primary key,
	`name`    varchar(255) not null
) ENGINE=InnoDB DEFAULT CHARSET=utf32 COLLATE=utf32_unicode_ci;

CREATE TABLE if not exists accounts
(
	`id`        varchar(255) NOT NULL,
	`birthday`  datetime(6) DEFAULT NULL,
	`email`     varchar(255) DEFAULT NULL,
	`full_name` varchar(255) DEFAULT NULL,
	`gender`    varchar(255) DEFAULT NULL,
	`password`  varchar(255) NOT NULL,
	`security`  varchar(255) DEFAULT NULL,
	`status`    bit(1)       NOT NULL,
	`username`  varchar(255) NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf32 COLLATE=utf32_unicode_ci;

CREATE TABLE if not exists accounts_roles (
	account_id varchar(255) NOT NULL,
	roles_id varchar(255) NOT NULL,
	PRIMARY KEY (account_id, roles_id),
	CONSTRAINT FOREIGN KEY (roles_id) REFERENCES roles (id) )
	ENGINE=InnoDB DEFAULT CHARSET=utf32 COLLATE=utf32_unicode_ci;
