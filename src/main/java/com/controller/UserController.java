package com.controller;

import com.extra.GoogleVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.model.User;
import com.service.ExperienceService;
import com.service.UserService;
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

    /**
     * If the user isn't registered it creates an account, returns user inforamtions in both cases
     * @param model
     * @param idTokenString Google login token
     * @return  "login" (name, surname and id) in case of success, "response" (error message) otherwise
     */
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
            return "login";
        }
        model.addAttribute("success", false);
        model.addAttribute("response", "Login non effettuato");
        return "response";
    }

    private User register(String id, String mail, String name, String surname) {
        try {
            return userService.addUser(id, mail, name, surname);
        } catch (ConstraintViolationException e) {
            return null;
        }
    }

    /**
     * Updates user informations with given parameters
     * @param model
     * @param idTokenString Google login token
     * @param name  user's name
     * @param surname   user's surname
     * @param roles user's main roles
     * @param mail  user's mail
     * @param birth user's birth date
     * @param nation    user's nation
     * @param image user's profile image
     * @return  "user" (user informations and experiences) in case of success, "response" (error message) otherwise
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateUser(Model model, @RequestParam String idTokenString, @RequestParam String name, @RequestParam String surname, @RequestParam String roles, @RequestParam String mail, @RequestParam long birth, @RequestParam String nation, @RequestParam String image) {
        GoogleIdToken idToken = GoogleVerifier.verify(idTokenString);
        if (idToken != null) {
            User u = userService.updateUser(idToken.getPayload().getSubject(), name, surname, roles, mail, new Date(birth), nation, image);
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

    /**
     * Creates an experience and adds it to user's CV
     * @param model
     * @param title project title
     * @param genres    project genres
     * @param role  user's role in given project
     * @param start experience start date
     * @param end   experience start date
     * @param idTokenString Google login token
     * @return  "experience" (created experience informations) in case of success, "response" (error message) otherwise
     */
    @RequestMapping(value = "/experience", method = RequestMethod.POST)
    public String addExperience(Model model, @RequestParam String title, @RequestParam String genres, @RequestParam String role, @RequestParam int start, @RequestParam int end, @RequestParam String idTokenString) {
        GoogleIdToken idToken = GoogleVerifier.verify(idTokenString);
        try {
            String userId = idToken.getPayload().getSubject();
            if (userService.getUserById(userId) == null) {
                model.addAttribute("success", false);
                model.addAttribute("response", "Aggiunta dell'esperienza fallita: utente inesistente");
            } else {
                model.addAttribute("experience", experienceService.addExperience(title, genres, role, start, end, userId));
                return "experience";
            }
        } catch (ConstraintViolationException e) {
            model.addAttribute("success", false);
            model.addAttribute("response", "Aggiunta dell'esperienza fallita");
        }
        return "response";
    }

    /**
     * Returns informations of the user with the given id
     * @param model
     * @param id    user's id
     * @return  "user" (user informations and experiences) in case of success, "response" (error message) otherwise
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public String getUserById(Model model, @PathVariable String id) {
        User user = userService.getUserById(id);
        if (user == null) {
            model.addAttribute("success", false);
            model.addAttribute("response", "Utente inesistente");
            return "response";
        }
        model.addAttribute("user", user);
        model.addAttribute("age", new Period(new DateTime(user.getBirth()), new DateTime()).getYears());
        model.addAttribute("experiences", experienceService.listExperiencesByUser(id));
        return "user";
    }

    /**
     * Returns a list of users based on the given parameters
     * @param model
     * @param name  user's name
     * @param surname   user's surname
     * @param roles user's main roles
     * @param minAge    user's minimum age
     * @param maxAge    user's maximum age
     * @param project   id of the project the user worked at
     * @param genres    genres of the project the user worked at
     * @param order     type of order (surname, birth)
     * @param asc   ascendant or descendant
     * @return  "userList" (list of users) in case of success
     */
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public String advancedUserSearch(Model model, @RequestParam String name, @RequestParam String surname, @RequestParam String roles, @RequestParam int minAge, @RequestParam int maxAge, @RequestParam String project, @RequestParam String genres, @RequestParam String order, @RequestParam boolean asc) {
        model.addAttribute("users", userService.advancedUserSearch(name, surname, roles, minAge, maxAge, project, genres, order, asc));
        return "userList";
    }
}
