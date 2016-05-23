package com.myvladimir.usertable.controller;

import com.myvladimir.usertable.model.User;
import com.myvladimir.usertable.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Controller
public class UserController {

    public UserController() {
        System.out.println("UserController()");
    }

    @Autowired
    private UserService userService;



    @RequestMapping("createUser")
    public ModelAndView createUser(@ModelAttribute User user) {
        Date date = new Date();
        user.setCreatedDate(new Timestamp(date.getTime()));
        return new ModelAndView("userForm", "userObject", user);
    }

    @RequestMapping(value= {"getAllUsers", "/"})
    public ModelAndView listOfUsers(@RequestParam(required = false) Integer page) {
        ModelAndView modelAndView = new ModelAndView("users");

        List<User> userList = userService.listUsers();
        PagedListHolder<User> pagedListHolder = new PagedListHolder<>(userList);
        pagedListHolder.setPageSize(10);
        modelAndView.addObject("maxPages", pagedListHolder.getPageCount());

        if(page==null || page < 1 || page > pagedListHolder.getPageCount())page=1;

        modelAndView.addObject("page", page);
        if(page == null || page < 1 || page > pagedListHolder.getPageCount()){
            pagedListHolder.setPage(0);
            modelAndView.addObject("userList", pagedListHolder.getPageList());
        }
        else if(page <= pagedListHolder.getPageCount()) {
            pagedListHolder.setPage(page-1);
            modelAndView.addObject("userList", pagedListHolder.getPageList());
        }
        return modelAndView;
    }

    @RequestMapping("editUser")
    public ModelAndView editUser(@RequestParam int id, @ModelAttribute User user) {
        System.out.println("Зашли в контроллер для редактирования юзера, дата из модели у него " + user.getCreatedDate());
        user = userService.getUserById(id);
        Date dateTmp = user.getCreatedDate();
        System.out.println("А дата у передаваемого, определённая по id " + dateTmp);
        return new ModelAndView("userForm", "userObject", user);
    }

    @RequestMapping("saveUser")
    public ModelAndView saveUser(@ModelAttribute User user) {
        if(user.getId() == 0){
            System.out.println("Юзер новый, добавим");
            userService.addUser(user);
        } else {
            System.out.println("Юзер уже есть в базе, отредактируем");
            userService.updateUser(user);
        }
        return new ModelAndView("redirect:getAllUsers");
    }

    @RequestMapping("deleteUser")
    public ModelAndView deleteUser(@RequestParam int id) {
        userService.removeUser(id);
        return new ModelAndView("redirect:getAllUsers");
    }

    @RequestMapping("searchUser")
    public ModelAndView searchUser(@RequestParam("searchName") String searchName) {
        List<User> userList = userService.listUsers(searchName);
        return new ModelAndView("users", "userList", userList);
    }
}