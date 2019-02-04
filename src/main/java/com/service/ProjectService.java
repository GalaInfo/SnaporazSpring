package com.service;

import com.model.Project;
import java.util.List;

public interface ProjectService {

    public Project getProjectById(int id);
    
    public List<Project> listProjectsByTitle(String title);
    
    public List<Project> listMostFoundedProjects();
    
    public List<Project> listMostRecentProjects();
    
    public List<Project> listNearestProjects();

}