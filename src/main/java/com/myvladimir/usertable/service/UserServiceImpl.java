package com.myvladimir.usertable.service;

import com.myvladimir.usertable.dao.UserDao;
import com.myvladimir.usertable.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public void addUser(User user) {
        System.out.println("Добавляем юзера в UserService");
        this.userDao.addUser(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
    this.userDao.updateUser(user);
    }

    @Override
    @Transactional
    public void removeUser(int id) {
    this.userDao.removeUser(id);
    }

    @Override
    @Transactional
    public User getUserById(int id) {
        return this.userDao.getUserById(id);
    }

    @Override
    @Transactional
    public List<User> listUsers() {
        return this.userDao.listUsers();
    }

    @Override
    @Transactional
    public List<User> listUsers(String userName) {
        return this.userDao.listUsers(userName);
    }
}
