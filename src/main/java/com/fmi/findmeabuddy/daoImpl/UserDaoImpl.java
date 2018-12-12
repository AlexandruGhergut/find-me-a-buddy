package com.fmi.findmeabuddy.daoImpl;

import com.fmi.findmeabuddy.dao.UserDao;
import com.fmi.findmeabuddy.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;



@Repository("userDao")
public class UserDaoImpl implements UserDao {

    @Autowired
    SessionFactory sessionFactory;

    protected Session currentSession(){
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Account findUser(Long accountId) {
        return (Account) currentSession().get(Account.class, accountId);
    }
}
