CREATE TABLE `City` (
    `id` int(11) AUTO_INCREMENT,
    `region_id` int(11) NOT NULL,
    `country_id` smallint(5) NOT NULL,
    `latitude` decimal(10,8) NOT NULL,
    `longitude` decimal(11,8) NOT NULL,
    `name` varchar(255) NOT NULL,
    PRIMARY KEY(`id`)
);
