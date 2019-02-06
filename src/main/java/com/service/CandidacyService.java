package com.service;

import com.model.Candidacy;
import java.util.List;

public interface CandidacyService {
    
    public void addCandidacy(Candidacy c);
    
    public List<Candidacy> listCandidaciesByPart(int id);
}
