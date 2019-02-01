package com.service;

import com.model.Project;
import java.util.List;

public interface ProjectService {

    public List<Project> listProjectsByTitle(String title);

}