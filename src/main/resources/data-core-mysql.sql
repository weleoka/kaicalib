SET FOREIGN_KEY_CHECKS=0;
-- SET REFERENTIAL_INTEGRITY FALSE; -- HSQLDB


INSERT INTO `Copy` (`copy_id`, `copy_created_at`, `curr_status`, `uuid`, `copyType_copy_type_id`, `title_title_id`) VALUES
(3, '2019-05-17', 'available', unhex(replace('19c87ebd-8c2a-46ad-a278-b4eb93ea562c','-','')), 4, 2),
(5, '2019-05-17', 'available', unhex(replace('6e5eb0ee-d821-465f-bbc8-bd0081e2a90b','-','')), 6, 2),
(7, '2019-05-17', 'available', unhex(replace('37d4cd88-c487-46b3-984b-b58eb14b3fc7','-','')), 8, 2),
(9, '2019-05-17', 'available', unhex(replace('225fa17a-26db-4495-893a-9a00a7a92979','-','')), 10, 2),
(11, '2019-05-17', 'available', unhex(replace('da469629-9a3c-42d7-aae9-8ceaca973f55','-','')), 12, 1),
(13, '2019-05-17', 'available', unhex(replace('39d49ea6-de19-4eef-b08f-ab985dae7147','-','')), 14, 1),
(15, '2019-05-17', 'available', unhex(replace('70e7eacc-41f6-4c7d-bbe9-dd1e2da2fe6c','-','')), 16, 1),
(17, '2019-05-17', 'available', unhex(replace('1517dfef-ef7f-4531-acd0-c9dc0574bddd','-','')), 18, 1),
(19, '2019-05-17', 'available', unhex(replace('e0052621-64e6-47e6-9adc-b8a0d8f377f2','-','')), 20, 1),
(21, '2019-05-17', 'available', unhex(replace('73993972-04d7-4dbd-8a21-502e0bb40bd2','-','')), 22, 1),
(24, '2019-05-17', 'available', unhex(replace('200133b0-df64-474c-b201-6421cfdd7bbd','-','')), 25, 23),
(26, '2019-05-17', 'available', unhex(replace('0065894e-64d2-4a9b-a007-d09305abaeab','-','')), 27, 23),
(28, '2019-05-17', 'available', unhex(replace('c8167b77-4b8f-41be-b1ce-0539dd82c6f4','-','')), 29, 23);

INSERT INTO `Copy_type` (`type_discriminator`, `copy_type_id`, `loan_time`, `uuid`, `copy_copy_id`) VALUES
('NORMAL', 4, 4, unhex(replace('977eed97-d812-4614-992f-7da27ce68b7e','-','')), 3),
('NORMAL', 6, 4, unhex(replace('87815c88-0097-43cb-a9f7-2d2c38d6493a','-','')), 5),
('NORMAL', 8, 4, unhex(replace('3467d82c-243d-4c69-b219-5e1d0d7b48c9','-','')), 7),
('NORMAL', 10, 4, unhex(replace('b189d195-2362-427c-8c0a-48fb7dd32c8c','-','')), 9),
('NORMAL', 12, 4, unhex(replace('1c51f6d1-4f83-4083-94ec-d49a4f25b5bc','-','')), 11),
('NORMAL', 14, 4, unhex(replace('83b0fa53-36b4-4608-a1be-b5449e92e5ea','-','')), 13),
('NORMAL', 16, 4, unhex(replace('d0f5a48b-c0b3-4fe3-9286-68783bb6304a','-','')), 15),
('NORMAL', 18, 4, unhex(replace('30ce6b00-338a-43e6-98a3-ac9077019fa6','-','')), 17),
('NORMAL', 20, 4, unhex(replace('f277e72b-84c5-40c3-b0c5-8525b37b65aa','-','')), 19),
('NORMAL', 22, 4, unhex(replace('075dc272-b273-4e46-bee7-2ab018ef93ca','-','')), 21),
('NORMAL', 25, 4, unhex(replace('c827ea44-4405-4f79-ba5a-c78149f0e632','-','')), 24),
('NORMAL', 27, 4, unhex(replace('2cc70e2b-0463-4f3e-8eb9-537e80e33434','-','')), 26),
('NORMAL', 29, 4, unhex(replace('c5e978c3-fece-4ae1-b29c-d5b871a9592b','-','')), 28);


INSERT INTO `Patron` (`patron_id`, `createdAt`, `user_uuid`, `uuid`) VALUES
-- unhex(replace(uuid(),'-',''))
-- (1, '2019-05-17', 'd23bb6cf-7f41-42f1-ad6b-3e046ef7c312', '7f23098d-c8cb-4f16-8514-f0d67d0fdad1'),
(1, '2019-05-17', unhex(replace('d23bb6cf-7f41-42f1-ad6b-3e046ef7c312','-','')), unhex(replace('7f23098d-c8cb-4f16-8514-f0d67d0fdad1','-',''))),
-- (2, '2019-03-17', '014ff25d-c132-4b95-8aac-e593e9878759', '07998a99-2508-4bf6-b550-0408ad4cf04b');
(2, '2019-03-17', unhex(replace('014ff25d-c132-4b95-8aac-e593e9878759','-','')), unhex(replace('07998a99-2508-4bf6-b550-0408ad4cf04b','-','')));


INSERT INTO `Loan` (`id`, `created_at`, `loan_return_date`, `uuid`, `copy_copy_id`, `patron_id`) VALUES
(1, '2019-05-17', '2019-07-17', unhex(replace('19c87ebd-8c2a-46ad-a278-b4eb93ea123c','-','')), 3, 2);

INSERT INTO `Title` (`title_id`, `created_at`, `title_name`, `uuid`) VALUES
(1, '2019-05-17', 'Java for N00bz', unhex(replace('1870ad2e-857a-4533-b09a-ce54e93f4b15','-',''))),
(2, '2019-05-17', 'Failing Gracefully at Spring MVC', unhex(replace('fe805f7f-414a-41a0-8139-7c362e41f6c4','-',''))),
(23, '2019-05-17', 'Shoddy Code - And How to Write It', unhex(replace('46c3793a-f3cd-4d4b-8f4c-6d70da5d4376','-','')));

-- INSERT INTO `Patron_Loan` (`Patron_patron_id`, `loans_id`) VALUES (2, 1); -- removed as there is no manytomany patron - loan.

-- Depreciated with Mariadb 10.3 and sequences support
-- SET SQL_SAFE_UPDATES = 0;
-- UPDATE `hibernate_sequence` SET `next_val` = 30;
-- SET SQL_SAFE_UPDATES = 1;

-- MariaDB 10.3, H2 and HSQLDB sequences
ALTER SEQUENCE hibernate_sequence RESTART WITH 30;


SET FOREIGN_KEY_CHECKS=1;
-- SET REFERENTIAL_INTEGRITY TRUE;
