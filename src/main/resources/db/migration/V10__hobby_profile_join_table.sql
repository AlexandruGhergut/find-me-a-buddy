CREATE TABLE `ProfileHobby` (
    `hobby_id` int(11) NOT NULL,
    `profile_id` bigint(20) NOT NULL,
    PRIMARY KEY(`hobby_id`, `profile_id`)
);

ALTER TABLE `ProfileHobby`
ADD CONSTRAINT `fk_ProfileHobby_Hobby` FOREIGN KEY (`hobby_id`) REFERENCES `Hobby` (`hobby_id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `ProfileHobby`
ADD CONSTRAINT `fk_ProfileHobby_Profile` FOREIGN KEY (`profile_id`) REFERENCES `Profile` (`account_id`) ON DELETE CASCADE ON UPDATE CASCADE;

-- Dropping multiple columns differs across SQL flavors so we'll drop one column per statement
ALTER TABLE `Profile`
DROP COLUMN `hobby_one`;
ALTER TABLE `Profile`
DROP COLUMN `hobby_two`;
ALTER TABLE `Profile`
DROP COLUMN `hobby_three`;
ALTER TABLE `Profile`
DROP COLUMN `hobby_four`;
ALTER TABLE `Profile`
DROP COLUMN `hobby_five`;

ALTER Table `Profile`
MODIFY `city_id` int(11) NOT NULL;




