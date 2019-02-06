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
    public void addCandidacy(Candidacy c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Transactional
    public List<Candidacy> listCandidaciesByPart(int id) {
        return candidacyDAO.listCandidaciesByPart(id);
    }
}
