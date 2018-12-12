package com.fmi.findmeabuddy.Service;

import com.fmi.findmeabuddy.model.Account;

public interface UserService {
    public Account findUser(Long accountId);
    public Account loginUser(Long accountId, String password);
}
