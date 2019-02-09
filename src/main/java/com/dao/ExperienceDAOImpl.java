/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.model.Experience;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Marco
 */
public class ExperienceDAOImpl implements ExperienceDAO {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    @Override
    public void addExperience(Experience e) {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(e);
    }

    @Override
    public List<Experience> listExperiencesByUser(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        return session.createCriteria(Experience.class).add(Restrictions.eq("user", id)).list();
    }
}
