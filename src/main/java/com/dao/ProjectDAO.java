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

    public void addProject(Project p);

    public void updateProject(Project p);

    public List<Project> listProjectsByTitle(String title);

    public Project getProjectById(int id);
    
    public List<Project> listMostFoundedProjects();
    
    public List<Project> listMostRecentProjects();
    
    public List<Project> listClosestProjects();
}
