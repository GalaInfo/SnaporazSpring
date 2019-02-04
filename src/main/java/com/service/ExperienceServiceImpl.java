package com.service;

import com.dao.ExperienceDAO;
import com.model.Experience;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExperienceServiceImpl implements ExperienceService {

    @Autowired
    private ExperienceDAO experienceDAO;

    public void setExperienceDAO(ExperienceDAO experienceDAO) {
        this.experienceDAO = experienceDAO;
    }

    @Override
    @Transactional
    public List<Experience> listExperiencesByUser(int id) {
        return experienceDAO.listExperiencesByUser(id);
    }

}
