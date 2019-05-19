SET FOREIGN_KEY_CHECKS=0;
-- USE `bibsys_accounts_db`;

INSERT INTO `roles` (`id`, `role`)
VALUES
(1, 'ADMIN'),
(2, 'PATRON');

-- aaa has pwd bbb and bbb has pwd ccc
-- $2a$10$/SfLMpnxYqZ3nIGeTJhMg.tbu6sMb9B/5JFo3U4RORXtGDlS43dt6 is bbb
-- $2a$10$wBgT3v1fQIHUgyr042br4eyfg3.p4tYhFJLkr8DnmFNEWXutgYB5m is ccc
INSERT INTO `user` (`id`, `active`, `email`, `firstname`, `lastname`, `address`, `password`, `patron_uuid`, `username`, `uuid`) VALUES
(1, 1, 'info@patron.com', 'Joe', 'Patron', 'Stenungnen 22', '$2a$10$/SfLMpnxYqZ3nIGeTJhMg.tbu6sMb9B/5JFo3U4RORXtGDlS43dt6', '9b99623c-36b7-4701-94e0-422c056c2496', 'aaa', '6743a289-ac08-4a6f-87df-393ba95b86cc'),
(2, 1, 'hilda@patron.com', 'Hilda', 'Holm', 'Hejgatan 42', '$2a$10$wBgT3v1fQIHUgyr042br4eyfg3.p4tYhFJLkr8DnmFNEWXutgYB5m', '571314cf-2c87-4200-a282-e0ddec913513', 'bbb', 'd4949f00-7c1e-4521-a99e-74f30fbf193a'),
(3, 1, 'info@admin.com', 'Biblio', 'Tikarie', 'Svejgatan 9', '$2a$10$wBgT3v1fQIHUgyr042br4eyfg3.p4tYhFJLkr8DnmFNEWXutgYB5m', null, 'admin', 'b04269d2-af7f-4a37-a441-6ac66adf21ff');

INSERT INTO user_roles (user_id, role_id) VALUES (1, 2);
INSERT INTO user_roles (user_id, role_id) VALUES (2, 2);
INSERT INTO user_roles (user_id, role_id) VALUES (3, 1);

-- H2 DB Syntax: ALTER SEQUENCE hibernate_sequence RESTART WITH 3;

SET SQL_SAFE_UPDATES = 0;
UPDATE `hibernate_sequence` SET `next_val` = 4;
SET SQL_SAFE_UPDATES = 1;

SET FOREIGN_KEY_CHECKS=1;
