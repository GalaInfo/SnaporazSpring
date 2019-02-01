package com.service;

import com.dao.ProjectDAO;
import com.model.Project;
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
    public List<Project> listProjectsByTitle(String title) {
        return projectDAO.listProjectsByTitle(title);
    }

}
