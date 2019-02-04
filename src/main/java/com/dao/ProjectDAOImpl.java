/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.model.Project;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Marco
 */
public class ProjectDAOImpl implements ProjectDAO{

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf) {
        this.sessionFactory = sf;
    }
    
    @Override
    public void addProject(Project p) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateProject(Project p) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Project> listProjectsByTitle(String title) {
        Session session = this.sessionFactory.getCurrentSession();
        return session.createCriteria(Project.class).add(Restrictions.like("title", title, MatchMode.ANYWHERE)).list();
    }

    @Override
    public Project getProjectById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        return (Project) session.createCriteria(Project.class).add(Restrictions.eq("id", id)).uniqueResult();
    }

    @Override
    public List<Project> listMostFoundedProjects() {
        Session session = this.sessionFactory.getCurrentSession();
        return session.createCriteria(Project.class).addOrder(Order.desc("actual")).setMaxResults(3).list();
    }

    @Override
    public List<Project> listMostRecentProjects() {
        Session session = this.sessionFactory.getCurrentSession();
        return session.createCriteria(Project.class).addOrder(Order.desc("deadLine")).setMaxResults(3).list();
    }

    @Override
    public List<Project> listNearestProjects() {
        Session session = this.sessionFactory.getCurrentSession();
        return session.createCriteria(Project.class).add(Restrictions.ltProperty("actual", "min")).addOrder(Order.desc("actual")).setMaxResults(3).list();
    }
    
    
}
