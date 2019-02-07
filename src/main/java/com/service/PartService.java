package com.service;

import com.model.Part;
import java.util.List;

public interface PartService {
    
    public void addPart(int project, String role, String character);

    public void updatePart(int id, int user);
    
    public Part getPartById(int id);

    public List<Part> listPartsByProject(int id);
}