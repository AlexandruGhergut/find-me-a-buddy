package com.fmi.findmeabuddy.serviceImpl;

import com.fmi.findmeabuddy.Service.UserService;
import com.fmi.findmeabuddy.dao.UserDao;
import com.fmi.findmeabuddy.model.Account;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    @Override
    public Account findUser(Long accountId) {
        return userDao.findUser(accountId);
    }

    @Override
        public Account loginUser(Long accountId, String password){
            Account user = this.findUser(accountId);
            if (user != null & user.getPassword().equals(password)){
                return user;
            }
            return null;
        }
    }
}
