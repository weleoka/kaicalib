SET FOREIGN_KEY_CHECKS=0;

INSERT INTO `copy` (`copy_id`, `copy_created_at`, `copy_return_date`, `status`, `uuid`, `copyType_copy_type_id`, `title_title_id`) VALUES
(3, '2019-05-17', '2019-05-17', 'available', '19c87ebd-8c2a-46ad-a278-b4eb93ea562c', 4, 2),
(5, '2019-05-17', '2019-05-17', 'available', '6e5eb0ee-d821-465f-bbc8-bd0081e2a90b', 6, 2),
(7, '2019-05-17', '2019-05-17', 'available', '37d4cd88-c487-46b3-984b-b58eb14b3fc7', 8, 2),
(9, '2019-05-17', '2019-05-17', 'available', '225fa17a-26db-4495-893a-9a00a7a92979', 10, 2),
(11, '2019-05-17', '2019-05-17', 'available', 'da469629-9a3c-42d7-aae9-8ceaca973f55', 12, 1),
(13, '2019-05-17', '2019-05-17', 'available', '39d49ea6-de19-4eef-b08f-ab985dae7147', 14, 1),
(15, '2019-05-17', '2019-05-17', 'available', '70e7eacc-41f6-4c7d-bbe9-dd1e2da2fe6c', 16, 1),
(17, '2019-05-17', '2019-05-17', 'available', '1517dfef-ef7f-4531-acd0-c9dc0574bddd', 18, 1),
(19, '2019-05-17', '2019-05-17', 'available', 'e0052621-64e6-47e6-9adc-b8a0d8f377f2', 20, 1),
(21, '2019-05-17', '2019-05-17', 'available', '73993972-04d7-4dbd-8a21-502e0bb40bd2', 22, 1),
(24, '2019-05-17', '2019-05-17', 'available', '200133b0-df64-474c-b201-6421cfdd7bbd', 25, 23),
(26, '2019-05-17', '2019-05-17', 'available', '0065894e-64d2-4a9b-a007-d09305abaeab', 27, 23),
(28, '2019-05-17', '2019-05-17', 'available', 'c8167b77-4b8f-41be-b1ce-0539dd82c6f4', 29, 23);

INSERT INTO `copy_type` (`type_discriminator`, `copy_type_id`, `loan_time`, `uuid`, `copy_copy_id`) VALUES
('NORMAL', 4, 4, '977eed97-d812-4614-992f-7da27ce68b7e', 3),
('NORMAL', 6, 4, '87815c88-0097-43cb-a9f7-2d2c38d6493a', 5),
('NORMAL', 8, 4, '3467d82c-243d-4c69-b219-5e1d0d7b48c9', 7),
('NORMAL', 10, 4, 'b189d195-2362-427c-8c0a-48fb7dd32c8c', 9),
('NORMAL', 12, 4, '1c51f6d1-4f83-4083-94ec-d49a4f25b5bc', 11),
('NORMAL', 14, 4, '83b0fa53-36b4-4608-a1be-b5449e92e5ea', 13),
('NORMAL', 16, 4, 'd0f5a48b-c0b3-4fe3-9286-68783bb6304a', 15),
('NORMAL', 18, 4, '30ce6b00-338a-43e6-98a3-ac9077019fa6', 17),
('NORMAL', 20, 4, 'f277e72b-84c5-40c3-b0c5-8525b37b65aa', 19),
('NORMAL', 22, 4, '075dc272-b273-4e46-bee7-2ab018ef93ca', 21),
('NORMAL', 25, 4, 'c827ea44-4405-4f79-ba5a-c78149f0e632', 24),
('NORMAL', 27, 4, '2cc70e2b-0463-4f3e-8eb9-537e80e33434', 26),
('NORMAL', 29, 4, 'c5e978c3-fece-4ae1-b29c-d5b871a9592b', 28);

SET SQL_SAFE_UPDATES = 0;
UPDATE `hibernate_sequence` SET `next_val` = 30;
SET SQL_SAFE_UPDATES = 1;

INSERT INTO `patron` (`patron_id`, `createdAt`, `user_uuid`, `uuid`) VALUES
(1, '2019-05-17', '6743a289-ac08-4a6f-87df-393ba95b86cc', '9b99623c-36b7-4701-94e0-422c056c2496'),
(2, '2019-03-17', 'd4949f00-7c1e-4521-a99e-74f30fbf193a', '571314cf-2c87-4200-a282-e0ddec913513');

INSERT INTO `loan` (`id`, `createdAt`, `returnDate`, `uuid`, `copy_copy_id`, `patron_patron_id`) VALUES
(1, '2019-05-17', '2019-07-17', '19c87ebd-8c2a-46ad-a278-b4eb93ea123c', 3, 2);

INSERT INTO `title` (`title_id`, `created_at`, `title_name`, `uuid`) VALUES
(1, '2019-05-17', 'Java for N00bz', '1870ad2e-857a-4533-b09a-ce54e93f4b15'),
(2, '2019-05-17', 'Failing Gracefully at Spring MVC', 'fe805f7f-414a-41a0-8139-7c362e41f6c4'),
(23, '2019-05-17', 'Shoddy Code - And How to Write It', '46c3793a-f3cd-4d4b-8f4c-6d70da5d4376');

INSERT INTO `patron_loan` (`Patron_patron_id`, `loans_id`) VALUES (2, 1);

SET FOREIGN_KEY_CHECKS=1;
