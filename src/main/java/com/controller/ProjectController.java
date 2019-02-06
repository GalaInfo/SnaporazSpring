package com.controller;

import com.model.Candidacy;
import com.model.Part;
import com.model.Project;
import com.model.User;
import com.service.CandidacyService;
import com.service.PartService;
import com.service.ProjectService;
import com.service.UserService;
import java.util.ArrayList;
import java.util.List;
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
public class ProjectController {

    private ProjectService projectService;
    private PartService partService;
    private CandidacyService candidacyService;
    private UserService userService;

    @Autowired(required = true)
    @Qualifier(value = "projectService")
    public void setProjectService(ProjectService s) {
        this.projectService = s;
    }

    @Autowired(required = true)
    @Qualifier(value = "partService")
    public void setPartService(PartService s) {
        this.partService = s;
    }

    @Autowired(required = true)
    @Qualifier(value = "candidacyService")
    public void setCandidacyService(CandidacyService s) {
        this.candidacyService = s;
    }

    @Autowired(required = true)
    @Qualifier(value = "userService")
    public void setUserService(UserService s) {
        this.userService = s;
    }

    @RequestMapping(value = "/")
    public String mainPage(Model model) {
        model.addAttribute("mostFoundedProjects", projectService.listMostFoundedProjects());
        model.addAttribute("mostRecentProjects", projectService.listMostRecentProjects());
        model.addAttribute("closestProjects", projectService.listClosestProjects());
        return "home";
    }

    @RequestMapping(value = "/project/{id}", method = RequestMethod.GET)
    public String getProjectById(Model model, @PathVariable int id) {
        Project pr = projectService.getProjectById(id);
        model.addAttribute("project", pr);
        List<Part> parts = partService.listPartsByProject(id);
        model.addAttribute("parts", parts);
        for (Part p : parts) {
            List<User> users = new ArrayList<>();
            for (Candidacy c : candidacyService.listCandidaciesByPart(p.getId())) {
                users.add(userService.getUserById(c.getUser()));
            }
            model.addAttribute("part" + p.getId(), users);
        }
        model.addAttribute("related", projectService.listRelatedProjects(id, pr.getGenres()));
        return "project";
    }

    @RequestMapping(value = "/project", method = RequestMethod.POST)
    public String addProject(Model model, @RequestParam String title, @RequestParam String genres, @RequestParam String plot, @RequestParam String img, @RequestParam long min, @RequestParam String prizes, @RequestParam int owner) {
        try {
            int projectId = projectService.addProject(title, genres, plot, img, min, prizes, owner);
            return getProjectById(model, projectId);
        } catch (ConstraintViolationException e) {
            model.addAttribute("success", false);
            model.addAttribute("response", "Creazione del progetto fallita");
        }
        return "response";
    }

    @RequestMapping(value = "/test/project", method = RequestMethod.GET)
    public String addProject(Model model) {
        try {
            int projectId = projectService.addProject("Quei Bravi Ragazzi", "Dramma", "Bella", "", 1000.0, "Niente", 7);
            return getProjectById(model, projectId);
        } catch (ConstraintViolationException e) {
            model.addAttribute("success", false);
            model.addAttribute("response", "Creazione del progetto fallita");
        }
        return "response";
    }

    @RequestMapping(value = "/projects/{title}", method = RequestMethod.GET)
    public String listProjecstByTitle(Model model, @PathVariable String title) {
        model.addAttribute("projects", projectService.listProjectsByTitle(title));
        return "projectsList";
    }

    @RequestMapping(value = "/projects", method = RequestMethod.POST)
    public String advancedProjectSearch(Model model, @RequestParam String title, @RequestParam String owner, @RequestParam String genre, @RequestParam String collab, @RequestParam String order, @RequestParam boolean asc) {
        model.addAttribute("projects", projectService.advancedProjectSearch(title, owner, genre, collab, order, asc));
        return "projectsList";
    }

    @RequestMapping(value = "/test/projects", method = RequestMethod.GET)
    public String advancedProjectSearch(Model model) {
        model.addAttribute("projects", projectService.advancedProjectSearch(null, null, null, "Scorsese", null, true));
        return "projectsList";
    }
}
