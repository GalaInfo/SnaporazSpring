package com.controller;

import com.model.Candidacy;
import com.model.Part;
import com.model.User;
import com.service.CandidacyService;
import com.service.PartService;
import com.service.ProjectService;
import com.service.UserService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    public String getProjectByid(Model model, @PathVariable int id) {
        model.addAttribute("project", projectService.getProjectById(id));
        List<Part> parts= partService.listPartsByProject(id);
        model.addAttribute("parts", parts);
        for(Part p : parts){
            List<User> users = new ArrayList<>();
            for(Candidacy c : candidacyService.listCandidaciesByPart(p.getId()))
                users.add(userService.getUserById(c.getUser()));
            model.addAttribute("part" + p.getId(), users);
        }
        return "project";
    }

    @RequestMapping(value = "/projects/{title}", method = RequestMethod.GET)
    public String listProjecstByTitle(Model model, @PathVariable String title) {
        model.addAttribute("projects", projectService.listProjectsByTitle(title));
        return "projectsList";
    }
}