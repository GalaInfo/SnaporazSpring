/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.extra.OrderBySqlFormula;
import com.model.Project;
import java.util.Calendar;
import java.util.List;
import org.hibernate.Query;
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
    public int addProject(Project p) {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(p);
        return p.getId();
    }

    @Override
    public void updateProject(Project p) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Project> advancedProjectSearch(String title, String owner, String genre, String collab, String order, boolean asc) {
        Session session = this.sessionFactory.getCurrentSession();
        String hql = "SELECT DISTINCT pr FROM Project pr, User o";
        hql += !collab.isEmpty() ? ", User u, Part pa WHERE pr.id = pa.project AND u.id = pa.user AND  u.surname LIKE :collab" : " WHERE 1 = 1";
        
        hql += " AND o.id = pr.owner AND pr.title LIKE :title AND o.surname LIKE :owner AND pr.genres LIKE :genre ORDER BY ";
        
        hql += "title".equals(order) ? "pr.title" : "pr.actual";
        hql += asc ? " ASC" : " DESC";
                
        Query q = session.createQuery(hql);
        
        q.setString("title", "%" + title + "%");
        q.setString("owner", "%" + owner + "%");
        q.setString("genre", "%" + genre + "%");
        if(!collab.isEmpty()){
            q.setString("collab", "%" + collab + "%");
        }
        
        return q.list();
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
    public List<Project> listClosestProjects() {
        Session session = this.sessionFactory.getCurrentSession();
        return session.createCriteria(Project.class).add(Restrictions.ltProperty("actual", "min")).addOrder(OrderBySqlFormula.sqlFormula("(attuale / minimo) DESC")).setMaxResults(3).list();
    }
    
    @Override
    public List<Project> listRelatedProjects(String owner, String genres) {
        Session session = this.sessionFactory.getCurrentSession();        
        Calendar cal = Calendar.getInstance();
        return session.createCriteria(Project.class).add(Restrictions.gt("deadLine", cal.getTime())).add(Restrictions.disjunction().add(Restrictions.eq("owner", owner)).add(Restrictions.like("genres", genres, MatchMode.ANYWHERE))).addOrder(Order.asc("deadLine")).setMaxResults(3).list();
    }
}
