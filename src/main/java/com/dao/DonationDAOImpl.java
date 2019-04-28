/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.model.Donation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Marco
 */
public class DonationDAOImpl implements DonationDAO {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    @Override
    public void addDonation(Donation d) {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(d);
    }

    @Override
    public Donation getDonationById(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        return (Donation) session.createCriteria(Donation.class).add(Restrictions.eq("id", id)).uniqueResult();
    }
}
