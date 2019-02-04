package com.controller;

import com.service.ExperienceService;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    private UserService userService;
    private ExperienceService experienceService;

    @Autowired(required = true)
    @Qualifier(value = "userService")
    public void setUserService(UserService s) {
        this.userService = s;
    }
    
    @Autowired(required = true)
    @Qualifier(value = "experienceService")
    public void setExperienceService(ExperienceService s) {
        this.experienceService = s;
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public String getUsersById(Model model, @PathVariable int id) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("experiences", experienceService.listExperiencesByUser(id));
        return "user";
    }
    
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public String advancedUserSearch(Model model, @RequestParam String name, @RequestParam String surname, @RequestParam String roles, @RequestParam int minAge, @RequestParam int maxAge, @RequestParam String project, @RequestParam String genres, @RequestParam String order, @RequestParam boolean asc) {
        model.addAttribute("users", userService.advancedUserSearch(name, surname, roles, minAge, maxAge, project, genres, order, asc));
        return "userList";
    }
    
    @RequestMapping(value = "/test/users", method = RequestMethod.GET)
    public String testAdvancedUserSearch(Model model) {
        model.addAttribute("users", userService.advancedUserSearch("Martin", "Scorsese", null, 0, 100, null, null, null, false));
        return "userList";
    }
}
