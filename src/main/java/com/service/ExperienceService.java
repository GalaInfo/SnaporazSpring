package com.service;

import com.model.Experience;
import java.util.List;

public interface ExperienceService {
    
    public List<Experience> listExperiencesByUser(int id);  
}
