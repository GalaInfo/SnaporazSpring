package com.service;

import com.model.Experience;
import java.util.Date;
import java.util.List;

public interface ExperienceService {
    
    public void addExperience(String title, String genres, String role, String character, Date start, Date end, int user);
    
    public List<Experience> listExperiencesByUser(int id);  
}
