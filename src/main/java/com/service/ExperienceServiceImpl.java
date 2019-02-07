package com.service;

import com.dao.ExperienceDAO;
import com.model.Experience;
import java.util.Date;
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
    public void addExperience(String title, String genres, String role, String character, Date start, Date end, int user) {
        Experience e = new Experience();
        
        e.setTitle(title);
        e.setGenres(genres);
        e.setRole(role);
        e.setCharacter(character);
        e.setStart(start);
        e.setEnd(end);
        e.setUser(user);
        
        experienceDAO.addExperience(e);
    }

    @Override
    @Transactional
    public List<Experience> listExperiencesByUser(int id) {
        return experienceDAO.listExperiencesByUser(id);
    }

}
