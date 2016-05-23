package com.myvladimir.usertable.service;

import com.myvladimir.usertable.model.User;

import java.util.List;

public interface UserService {
    public void addUser(User user);

    public void updateUser(User user);

    public void removeUser(int id);

    public User getUserById(int id);

    public List<User> listUsers();

    public List<User> listUsers(String userName);
}
