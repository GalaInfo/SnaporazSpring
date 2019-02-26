package com.controller;

import com.extra.GoogleVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.model.User;
import com.service.ExperienceService;
import com.service.UserService;
import java.util.Calendar;
import java.util.Date;
import org.hibernate.exception.ConstraintViolationException;
import org.joda.time.DateTime;
import org.joda.time.Period;
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

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(Model model, @RequestParam String idTokenString) {
        GoogleIdToken idToken = GoogleVerifier.verify(idTokenString);
        if (idToken != null) {
            Payload payload = idToken.getPayload();
            User u = userService.getUserById(payload.getSubject());
            if (u == null) {
                u = register(payload.getSubject(), payload.get("email").toString(), payload.get("given_name").toString(), payload.get("family_name").toString());
            }
            model.addAttribute("user", u);
        }
        return "login";
    }

    private User register(String id, String mail, String name, String surname) {
        try {
            return userService.addUser(id, mail, name, surname);
        } catch (ConstraintViolationException e) {
            return null;
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    private String updateUser(Model model, @RequestParam String idTokenString, @RequestParam String name, @RequestParam String surname, @RequestParam String roles, @RequestParam String mail, @RequestParam Date birth, @RequestParam String nation, @RequestParam String image) {
        GoogleIdToken idToken = GoogleVerifier.verify(idTokenString);
        if (idToken != null) {
            User u = userService.updateUser(idToken.getPayload().getSubject(), name, surname, roles, mail, birth, nation, image);
            if (u == null) {
                model.addAttribute("success", false);
                model.addAttribute("response", "Aggiornamento utente fallito: utente inesistente");
                return "response";
            }
            model.addAttribute("user", u);
            return "user";
        }
        model.addAttribute("success", false);
        model.addAttribute("response", "Aggiornamento utente fallito: login non effettuato");
        return "response";

    }

    @RequestMapping(value = "/experience", method = RequestMethod.POST)
    public String addExperience(Model model, @RequestParam String title, @RequestParam String genres, @RequestParam String role, @RequestParam int start, @RequestParam int end, @RequestParam String idTokenString) {
        GoogleIdToken idToken = GoogleVerifier.verify(idTokenString);
        try {
            String userId = idToken.getPayload().getSubject();
            experienceService.addExperience(title, genres, role, start, end, userId);
            return getUserById(model, userId);
        } catch (ConstraintViolationException e) {
            model.addAttribute("success", false);
            model.addAttribute("response", "Aggiunta dell' esperienza fallita");
        }
        return "response";
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public String getUserById(Model model, @PathVariable String id) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("age", new Period(new DateTime(user.getBirth()), new DateTime()).getYears());
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
