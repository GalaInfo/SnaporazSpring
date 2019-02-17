package com.service;

import com.dao.ProjectDAO;
import com.model.Project;
import java.util.Calendar;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectDAO projectDAO;

    public void setProjectDAO(ProjectDAO projectDAO) {
        this.projectDAO = projectDAO;
    }

    @Override
    @Transactional
    public int addProject(String title, String genres, String plot, String img, double min, String prizes, String owner) {
        Project p = new Project();
        p.setTitle(title);
        p.setGenres(genres);
        p.setPlot(plot);
        p.setImg(img);
        p.setMin(min);
        p.setPrizes(prizes);
        p.setOwner(owner);
        
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 30);
        p.setDeadLine(cal.getTime());
        return projectDAO.addProject(p);
    }

    @Override
    @Transactional
    public Project getProjectById(int id) {
        return projectDAO.getProjectById(id);
    }

    @Override
    @Transactional
    public List<Project> advancedProjectSearch(String title, String owner, String genre, String collab, String order, boolean asc) {
        return projectDAO.advancedProjectSearch(title, owner, genre, collab, order, asc);
    }
    
    @Override
    @Transactional
    public List<Project> listProjectsByTitle(String title) {
        return projectDAO.listProjectsByTitle(title);
    }

    @Override
    @Transactional
    public List<Project> listProjectsByOwner(String owner) {
        return projectDAO.listProjectsByOwner(owner);
    }

    @Override
    @Transactional
    public List<Project> listProjectsByCollaborator(String collaborator) {
        return projectDAO.listProjectsByCollaborator(collaborator);
    }

    @Override
    @Transactional
    public List<Project> listMostFoundedProjects() {
        return projectDAO.listMostFoundedProjects();
    }

    @Override
    @Transactional
    public List<Project> listMostRecentProjects() {
        return projectDAO.listMostRecentProjects();
    }

    @Override
    @Transactional
    public List<Project> listClosestProjects() {
        return projectDAO.listClosestProjects();
    }

    @Override
    @Transactional
    public List<Project> listRelatedProjects(Project p) {
        return projectDAO.listRelatedProjects(p);
    }

}
