SET FOREIGN_KEY_CHECKS=0;
-- USE `bibsys_accounts_db`;

INSERT INTO `Role` (`id`, `role`)
VALUES
(1, 'ADMIN'),
(2, 'PATRON');

-- aaa has pwd bbb and bbb has pwd ccc
-- $2a$10$/SfLMpnxYqZ3nIGeTJhMg.tbu6sMb9B/5JFo3U4RORXtGDlS43dt6 is bbb
-- $2a$10$wBgT3v1fQIHUgyr042br4eyfg3.p4tYhFJLkr8DnmFNEWXutgYB5m is ccc
INSERT INTO `User` (`id`, `active`, `email`, `firstname`, `lastname`, `address`, `password`, `patron_uuid`, `username`, `uuid`) VALUES
(1, 1, 'info@patron.com', 'Joe', 'Patron', 'Stenungnen 22', '$2a$10$/SfLMpnxYqZ3nIGeTJhMg.tbu6sMb9B/5JFo3U4RORXtGDlS43dt6', unhex(replace('7f23098d-c8cb-4f16-8514-f0d67d0fdad1','-','')), 'aaa', unhex(replace('d23bb6cf-7f41-42f1-ad6b-3e046ef7c312','-',''))),
(2, 1, 'hilda@patron.com', 'Hilda', 'Holm', 'Hejgatan 42', '$2a$10$wBgT3v1fQIHUgyr042br4eyfg3.p4tYhFJLkr8DnmFNEWXutgYB5m', unhex(replace('07998a99-2508-4bf6-b550-0408ad4cf04b','-','')), 'bbb', unhex(replace('014ff25d-c132-4b95-8aac-e593e9878759','-',''))),
(3, 1, 'info@admin.com', 'Biblio', 'Tikarie', 'Svejgatan 9', '$2a$10$wBgT3v1fQIHUgyr042br4eyfg3.p4tYhFJLkr8DnmFNEWXutgYB5m', null, 'admin', unhex(replace('b04269d2-af7f-4a37-a441-6ac66adf21ff','-','')));

INSERT INTO user_roles (user_id, role_id) VALUES (1, 2);
INSERT INTO user_roles (user_id, role_id) VALUES (2, 2);
INSERT INTO user_roles (user_id, role_id) VALUES (3, 1);


-- Depreciated with Mariadb 10.3 and sequences support
-- SET SQL_SAFE_UPDATES = 0;
-- UPDATE `hibernate_sequence` SET `next_val` = 4;
-- SET SQL_SAFE_UPDATES = 1;

-- MariaDB 10.3, H2 and HSQLDB sequences
ALTER SEQUENCE hibernate_sequence RESTART WITH 4;

SET FOREIGN_KEY_CHECKS=1;
