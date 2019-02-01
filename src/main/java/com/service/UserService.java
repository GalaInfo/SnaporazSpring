package com.service;

import com.model.User;
import java.util.List;

public interface UserService {

    public List<User> listUsersBySurname(String surname);
    
    public List<User> advancedUserSearch(String name, String surname, String roles, int minAge, int maxAge, String project, String genres, String order, boolean asc);
}
