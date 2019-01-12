ALTER TABLE `Account`
ADD COLUMN `role` varchar(16) NOT NULL AFTER `password`;
