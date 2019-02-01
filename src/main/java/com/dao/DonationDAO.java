/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.model.Donation;
import java.util.List;

/**
 *
 * @author Neno
 */
public interface DonationDAO {

    public void addDonation(Donation p);

    public void updateDonation(Donation p);

    public List<Donation> listDonation();

    public Donation getDonationById(int id);

    public void removeDonation(int id);
}
