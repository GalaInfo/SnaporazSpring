package com.dao;

import com.model.User;
import java.util.Calendar;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl implements UserDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDAOImpl.class);
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    @Override
    public void addUser(User p) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateUser(User p) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User getUserById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> listUsersBySurname(String surname) {
        Session session = this.sessionFactory.getCurrentSession();
        List<User> list = session.createCriteria(User.class).add(Restrictions.eq("surname", surname)).list();
        for (User u : list) {
            LOGGER.info("User List::" + u);
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> advancedUserSearch(String name, String surname, String roles, int minAge, int maxAge, String project, String genres, String order, boolean asc) {
        Session session = this.sessionFactory.getCurrentSession();
        String hql = "SELECT u FROM User u";
        hql += roles != null || project != null || genres != null ? ", Experience e WHERE u.id = e.user AND e.role LIKE :role AND e.title LIKE :project AND e.genres LIKE :genres" : " WHERE 1 = 1";
        
        hql += " AND u.name LIKE :name AND u.surname LIKE :surname AND u.birth >= :minBirth AND u.birth <= :maxBirth ORDER BY ";
        
        hql += "surname".equals(order) ? "u.surname" : "u.birth";
        hql += asc ? " ASC" : " DESC";
                
        Query q = session.createQuery(hql);
        
        if(name != null)
            q.setString("name", "%" + name + "%");
        else
            q.setString("name", "%%");
        if(surname != null)
            q.setString("surname", "%" + surname + "%");
        else
            q.setString("surname", "%%");
        
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -maxAge);
        q.setDate("minBirth", cal.getTime());
        cal.add(Calendar.YEAR, maxAge - minAge);
        q.setDate("maxBirth", cal.getTime());

        if(roles != null || project != null || genres != null){
            if(roles != null)
                q.setString("role", "%" + roles + "%");
            else
                q.setString("role", "%%");
            if(project != null)
                q.setString("project", "%" + project + "%");
            else
                q.setString("project", "%%");
            if(genres != null)
                q.setString("genres", "%" + genres + "%");
            else
                q.setString("genres", "%%");
        }
        
        return q.list();
    }
}
