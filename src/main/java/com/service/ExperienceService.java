package com.service;

import com.model.Experience;
import java.util.List;

public interface ExperienceService {
    
    public void addExperience(String title, String genres, String role, String character, int start, int end, String user);
    
    public List<Experience> listExperiencesByUser(String id);  
}
