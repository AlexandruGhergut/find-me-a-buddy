ALTER TABLE `Profile`
ADD `last_name` varchar(64) NOT NULL DEFAULT 'Anonymous';

ALTER TABLE `Profile`
ADD `first_name` varchar(64) NOT NULL DEFAULT 'Anonymous';

ALTER TABLE `Profile`
MODIFY `gender` char(1) NULL;