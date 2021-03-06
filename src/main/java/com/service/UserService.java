package com.service;

import com.model.User;
import java.util.Date;
import java.util.List;

public interface UserService {
    
    public User addUser(String id, String mail, String name, String surname);
    
    public User updateUser(String id, String name, String surname, String roles, String mail, Date birth, String nation, String image);

    public User getUserById(String id);
    
    public List<User> listUsersBySurname(String surname);
    
    public List<User> advancedUserSearch(String name, String surname, String roles, int minAge, int maxAge, String project, String genres, String order, boolean asc);   
}
