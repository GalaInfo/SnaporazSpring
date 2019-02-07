/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.model.Candidacy;
import java.util.List;

/**
 *
 * @author Neno
 */
public interface CandidacyDAO {

    public void addCandidacy(Candidacy c);
    
    public Candidacy getCandidacyById(int id);

    public List<Candidacy> listCandidaciesByPart(int id);
}
