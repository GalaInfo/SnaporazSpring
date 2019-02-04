package com.service;

import com.model.Part;
import java.util.List;

public interface PartService {
    
    public void addPart(Part p);

    public void updatePart(Part p);

    public List<Part> listPartsByProject(int id);
}