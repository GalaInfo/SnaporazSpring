/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.model.Experience;
import java.util.List;

/**
 *
 * @author Neno
 */
public interface ExperienceDAO {

    public void addExperience(Experience p);

    public void updateExperience(Experience p);

    public List<Experience> listExperience();

    public Experience getExperienceById(int id);

    public void removeExperience(int id);
}
