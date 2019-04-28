/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.model.Project;
import java.util.List;

/**
 *
 * @author Neno
 */
public interface ProjectDAO {

    public int addProject(Project p);
    
    public void updateProject(Project p);

    public List<Project> advancedProjectSearch(String title, String owner, String genre, String collab, String order, boolean asc);

    public List<Project> listProjectsByTitle(String title);
    
    public List<Project> listProjectsByOwner(String owner);
    
    public List<Project> listProjectsByCollaborator(String collaborator);

    public Project getProjectById(int id);
    
    public List<Project> listMostFoundedProjects();
    
    public List<Project> listMostRecentProjects();
    
    public List<Project> listClosestProjects();
    
    public List<Project> listRelatedProjects(Project p);
    
}
