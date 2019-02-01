package com.dao;

import com.model.Experience;
import com.model.User;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
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
        Criteria c = session.createCriteria(Experience.class, "experience");
        c.createAlias("experience.user", "user");
        if(name != null)
            c.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
        if(surname != null)
            c.add(Restrictions.like("surname", surname, MatchMode.ANYWHERE));
        if(roles != null){
            String[] rolesArray = roles.split(",");
            for(String role : rolesArray)
                c.add(Restrictions.eq("role", role));
        }
        if(surname != null)
            c.add(Restrictions.like("surname", surname, MatchMode.ANYWHERE));
        return c.list();
    }
}
