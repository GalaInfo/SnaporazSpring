package com.service;

import com.model.Candidacy;
import java.util.List;

public interface CandidacyService {
    
    public void addCandidacy(int part, int user);
    
    public Candidacy getCandidacyById(int id);
    
    public List<Candidacy> listCandidaciesByPart(int id);
}
