package com.service;

import com.model.Project;
import java.util.List;

public interface ProjectService {
    
    public int addProject(String title, String genres, String plot, String img, double min, String prizes, int owner);

    public Project getProjectById(int id);
    
    public List<Project> advancedProjectSearch(String title, String owner, String genre, String collab, String order, boolean asc);
    
    public List<Project> listProjectsByTitle(String title);
    
    public List<Project> listMostFoundedProjects();
    
    public List<Project> listMostRecentProjects();
    
    public List<Project> listClosestProjects();

    public List<Project> listRelatedProjects(int owner, String genres);
}