CREATE TABLE `City` (
    `city_id` int(11),
    `region_id` int(11) NOT NULL,
    `country_id` smallint(5) NOT NULL,
    `latitude` decimal(10,8) NOT NULL,
    `longitude` decimal(11,8) NOT NULL,
    `name` varchar(255) NOT NULL,
    PRIMARY KEY(`city_id`)
);

ALTER TABLE `City`
ADD CONSTRAINT `fk_City_Country` FOREIGN KEY (`country_id`) REFERENCES `Country` (`country_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
