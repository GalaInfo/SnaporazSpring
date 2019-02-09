package com.dao;

import com.model.User;
import java.util.List;

public interface UserDAO {

    public void addUser(User u);

    public void updateUser(User u);

    public User getUserById(String id);

    public List<User> listUsersBySurname(String surname);

    public List<User> advancedUserSearch(String name, String surname, String roles, int minAge, int maxAge, String project, String genres, String order, boolean asc);
}
