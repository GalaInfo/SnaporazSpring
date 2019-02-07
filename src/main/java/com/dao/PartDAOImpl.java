/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.model.Part;
import com.model.Project;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Marco
 */
public class PartDAOImpl implements PartDAO{

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    @Override
    public void addPart(Part p) {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(p);
    }

    @Override
    public void updatePart(Part p) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(p);
    }

    @Override
    public Part getPartById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        return (Part) session.createCriteria(Part.class).add(Restrictions.eq("id", id)).uniqueResult();
    }

    @Override
    public List<Part> listPartsByProject(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        return session.createCriteria(Part.class).add(Restrictions.eq("project", id)).list();
    }
    
}
