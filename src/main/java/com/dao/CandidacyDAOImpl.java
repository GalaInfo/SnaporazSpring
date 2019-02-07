/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.model.Candidacy;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Marco
 */
public class CandidacyDAOImpl implements CandidacyDAO {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    @Override
    public void addCandidacy(Candidacy c) {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(c);
    }

    @Override
    public Candidacy getCandidacyById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        return (Candidacy) session.createCriteria(Candidacy.class).add(Restrictions.eq("id", id)).uniqueResult();
    }

    @Override
    public List<Candidacy> listCandidaciesByPart(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        return session.createCriteria(Candidacy.class).add(Restrictions.eq("part", id)).list();
    }
}
