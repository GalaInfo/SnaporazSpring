package com.service;

import com.dao.DonationDAO;
import com.model.Donation;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DonationServiceImpl implements DonationService {

    @Autowired
    private DonationDAO donationDAO;

    public void setDonationDAO(DonationDAO donationDAO) {
        this.donationDAO = donationDAO;
    }

    @Override
    @Transactional
    public void addDonation(String id, int project, String user, double sum) {
        Donation d = new Donation();
        d.setId(id);
        d.setProject(project);
        d.setUser(user);
        d.setSum(sum);
        
        donationDAO.addDonation(d);
    }

    @Override
    @Transactional
    public Donation getDonationById(String id) {
        return donationDAO.getDonationById(id);
    }
}
