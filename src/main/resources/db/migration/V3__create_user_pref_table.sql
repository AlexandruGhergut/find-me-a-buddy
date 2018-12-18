CREATE TABLE `Profile` (
    `account_id` bigint(20) AUTO_INCREMENT,
    `birthday` date NOT NULL,
    `gender` char(1) NOT NULL,
    `hobby_one` varchar(64) NOT NULL,
    `hobby_two` varchar(64) NOT NULL,
    `hobby_three` varchar(64) NOT NULL,
    `hobby_four` varchar(64) NOT NULL,
    `hobby_five` varchar(64) NOT NULL,
    `country_name` varchar(255) NOT NULL,
    `city_name` varchar(255) NOT NULL,
    PRIMARY KEY(`account_id`)
);