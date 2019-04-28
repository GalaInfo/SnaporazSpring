/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.model.Donation;

/**
 *
 * @author Neno
 */
public interface DonationDAO {

    public void addDonation(Donation d);
    
    public Donation getDonationById(String id);
}
