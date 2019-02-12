package com.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.model.User;
import com.service.ExperienceService;
import com.service.UserService;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.exception.ConstraintViolationException;
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
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory()).setAudience(Collections.singletonList("617542772314-keo0t31kssvhk31g3ghjhho21m8s53cm.apps.googleusercontent.com")).build();
        try {
            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                Payload payload = idToken.getPayload();
                User u = userService.getUserById(payload.getSubject());
                if (u == null) {
                    u = register(payload.getSubject(), payload.get("email").toString(), payload.get("given_name").toString(), payload.get("family_name").toString());
                }
                model.addAttribute("user", u);
            }
        } catch (GeneralSecurityException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            model.addAttribute("mail", "GeneralSecurityError");
        } catch (IOException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            model.addAttribute("mail", "IOException");
        }
        return "prova";
    }

    private User register(String id, String mail, String name, String surname) {
        try {
            return userService.addUser(id, mail, name, surname);
        } catch (ConstraintViolationException e) {
            return null;
        }
    }
    
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String info(Model model, HttpSession session) {
        System.out.println("Session: " + session.getId());
        return "prova";
    }

    @RequestMapping(value = "/experience", method = RequestMethod.POST)
    public String addExperience(Model model, @RequestParam String title, @RequestParam String genres, @RequestParam String role, @RequestParam String character, @RequestParam Date start, @RequestParam Date end, @RequestParam String user) {
        //user da togliere e prenderlo direttamente dalla sessione
        try {
            experienceService.addExperience(title, genres, role, character, start, end, user);
            return getUserById(model, user);
        } catch (ConstraintViolationException e) {
            model.addAttribute("success", false);
            model.addAttribute("response", "Aggiunta dell' esperienza fallita");
        }
        return "response";
    }

    @RequestMapping(value = "/test/experience", method = RequestMethod.GET)
    public String addExperience(Model model) {
        //user da togliere e prenderlo direttamente dalla sessione
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -30);
        Date start = cal.getTime();
        cal.add(Calendar.YEAR, 1);
        Date end = cal.getTime();
        try {
            experienceService.addExperience("Mean Streets", "Gangster", "Regista", null, start, end, "0");
            return getUserById(model, "0");
        } catch (ConstraintViolationException e) {
            model.addAttribute("success", false);
            model.addAttribute("response", "Aggiunta dell' esperienza fallita");
        }
        return "response";
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public String getUserById(Model model, @PathVariable String id) {
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
