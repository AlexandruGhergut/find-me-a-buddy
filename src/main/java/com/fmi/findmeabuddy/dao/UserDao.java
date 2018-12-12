package com.fmi.findmeabuddy.dao;

import com.fmi.findmeabuddy.model.Account;

public interface UserDao {
    public Account findUser(Long accountId);
}
