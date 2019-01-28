ALTER TABLE `ProfileHobby`
DROP FOREIGN KEY `fk_ProfileHobby_Profile`;

ALTER TABLE `ProfileHobby`
ADD CONSTRAINT `fk_ProfileHobby_Profile` FOREIGN KEY (`profile_id`) REFERENCES `Profile` (`profile_id`) ON DELETE CASCADE ON UPDATE CASCADE;
