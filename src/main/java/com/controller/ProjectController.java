package com.controller;

import com.service.PartService;
import com.service.ProjectService;
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

    @RequestMapping(value = "/")
    public String mainPage(Model model) {
        model.addAttribute("mostFoundedProjects", projectService.listMostFoundedProjects());
        model.addAttribute("mostRecentProjects", projectService.listMostRecentProjects());
        model.addAttribute("nearestProjects", projectService.listNearestProjects());
        return "home";
    }
    
    @RequestMapping(value = "/project/{id}", method = RequestMethod.GET)
    public String getProjectByid(Model model, @PathVariable int id) {
        model.addAttribute("project", projectService.getProjectById(id));
        model.addAttribute("parts", partService.listPartsByProject(id));
        return "project";
    }

    @RequestMapping(value = "/projects/{title}", method = RequestMethod.GET)
    public String listProjecstByTitle(Model model, @PathVariable String title) {
        model.addAttribute("projects", projectService.listProjectsByTitle(title));
        return "projectsList";
    }
}