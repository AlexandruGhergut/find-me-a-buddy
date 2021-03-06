CREATE TABLE `Profile` (
    `profile_id` bigint(20) AUTO_INCREMENT,
    `account_id` bigint(20),
    `birthday` date NOT NULL,
    `gender` char(1) NOT NULL,
    `hobby_one` varchar(64) NOT NULL,
    `hobby_two` varchar(64) NOT NULL,
    `hobby_three` varchar(64) NOT NULL,
    `hobby_four` varchar(64) NOT NULL,
    `hobby_five` varchar(64) NOT NULL,
    `city_id` int(11) NOT NULL,
    PRIMARY KEY(`profile_id`)
);

ALTER TABLE `Profile`
ADD CONSTRAINT `fk_Profile_Account` FOREIGN KEY (`account_id`) REFERENCES `Account` (`account_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `Profile`
ADD CONSTRAINT `fk_Profile_City` FOREIGN KEY (`city_id`) REFERENCES `City` (`city_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `Profile`
ADD CONSTRAINT uk_profile_account_id UNIQUE (account_id);
