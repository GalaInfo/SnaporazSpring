package com.service;

import com.dao.UserDAO;
import com.model.User;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    @Transactional
    public List<User> listUsersBySurname(String surname) {
        return userDAO.listUsersBySurname(surname);
    }
    
    @Override
    @Transactional
    public List<User> advancedUserSearch(String name, String surname, String roles, int minAge, int maxAge, String project, String genres, String order, boolean asc){
        return userDAO.advancedUserSearch(name, surname, roles, minAge, maxAge, project, genres, order, asc);
    }
}
