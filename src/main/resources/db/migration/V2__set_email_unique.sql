ALTER TABLE `Account`
ADD CONSTRAINT uk_account_email UNIQUE (email);
