package com.dao;

import com.model.User;
import java.util.List;

public interface UserDAO {

    public void addUser(User p);

    public void updateUser(User p);

    public User getUserById(int id);

    public List<User> listUsersBySurname(String surname);

    public List<User> advancedUserSearch(String name, String surname, String roles, int minAge, int maxAge, String project, String genres, String order, boolean asc);
}
