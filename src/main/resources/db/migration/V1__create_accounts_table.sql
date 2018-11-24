CREATE TABLE `Account` (
    `account_id` bigint(20) AUTO_INCREMENT,
    `email` varchar(256) NOT NULL,
    `password` varchar(128) NOT NULL,
    `created_at` datetime NOT NULL,
    `updated_at` datetime NOT NULL,
    PRIMARY KEY(`account_id`)
);