package com.service;

import com.model.Donation;

public interface DonationService {
    
    public void addDonation(String id, int project, String user, double sum);
    
    public Donation getDonationById(String id);
}