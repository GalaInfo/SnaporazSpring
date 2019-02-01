/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.model.Part;
import java.util.List;

/**
 *
 * @author Neno
 */
public interface PartDAO {

    public void addPart(Part p);

    public void updatePart(Part p);

    public List<Part> listParts();

    public Part getPartById(int id);

    public void removePart(int id);
}
