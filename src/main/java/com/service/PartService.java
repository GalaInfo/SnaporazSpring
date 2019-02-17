package com.service;

import com.model.Part;
import java.util.List;

public interface PartService {
    
    public Part addPart(int project, String role, String character);
    
    public Part addPart(int project, String user, String role, String character);

    public Part updatePart(int id, String user);
    
    public Part getPartById(int id);

    public List<Part> listPartsByProject(int id);
}