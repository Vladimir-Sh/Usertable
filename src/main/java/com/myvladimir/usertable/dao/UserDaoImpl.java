package com.myvladimir.usertable.dao;

import com.myvladimir.usertable.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao{

    private SessionFactory sessionFactory;
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public void addUser(User user) {
        System.out.println("Добавляем юзера в UserDao");
        Session session = this.sessionFactory.getCurrentSession();
        user.setCreatedDate(new Timestamp(new Date().getTime()));
        session.persist(user);
    }

    @Override
    public void updateUser(User user) {
        Session session = this.sessionFactory.getCurrentSession();
        System.out.println("В updateUser он пришел с датой " + user.getCreatedDate());
        session.update(user);
    }

    @Override
    public void removeUser(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        User user = (User)session.load(User.class, new Integer(id));

        if (user != null){
            session.delete(user);
        }
    }

    @Override
    public User getUserById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        User user = (User) session.load(User.class, new Integer(id));
        System.out.println("id=" + id + " resolved user = " + user.toString());
        return user;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        Session session = this.sessionFactory.getCurrentSession();
        List<User> usersList = session.createQuery("from User").list();
        return usersList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> listUsers(String userName) {
        Session session = this.sessionFactory.getCurrentSession();
        String query = "SELECT e.* FROM User e WHERE e.name like '%"+ userName +"%'";
        List<Object[]> userObjects = session.createSQLQuery(query).list();
        List<User> users = new ArrayList<User>();
        System.out.println("Нашли юзера, преобразуем тип");
        for(Object[] userObject: userObjects) {
            User user = new User();
            System.out.println("Преобразуем тип id");
            int id = (int) userObject[0];
            System.out.println("Преобразуем тип name");
            String name = (String) userObject[1];
            System.out.println("Преобразуем тип age");
            int age = (int) userObject[2];
            System.out.println("Преобразуем тип isAdmin");
            boolean isAdmin = (boolean) userObject[3];
            System.out.println("Преобразуем тип createdDate");
            Date createdDate = (Date) userObject[4];
            System.out.println("Тип createdDate преобразован успешно");

            user.setId(id);
            user.setName(name);
            user.setAge(age);
            user.setIsAdmin(isAdmin);
            user.setCreatedDate(new Timestamp(createdDate.getTime()));
            users.add(user);
        }
        System.out.println(users);
        return users;
    }
}
