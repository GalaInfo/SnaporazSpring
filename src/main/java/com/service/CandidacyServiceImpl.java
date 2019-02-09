package com.service;

import com.dao.CandidacyDAO;
import com.model.Candidacy;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CandidacyServiceImpl implements CandidacyService {

    @Autowired
    private CandidacyDAO candidacyDAO;

    public void setCandidacyDAO(CandidacyDAO candidacyDAO) {
        this.candidacyDAO = candidacyDAO;
    }

    @Override
    @Transactional
    public void addCandidacy(int part, String user) {
        Candidacy p = new Candidacy();
        p.setPart(part);
        p.setUser(user);
        
        candidacyDAO.addCandidacy(p);
    }

    @Override
    @Transactional
    public Candidacy getCandidacyById(int id) {
        return candidacyDAO.getCandidacyById(id);
    }
    
    @Override
    @Transactional
    public List<Candidacy> listCandidaciesByPart(int id) {
        return candidacyDAO.listCandidaciesByPart(id);
    }
}
