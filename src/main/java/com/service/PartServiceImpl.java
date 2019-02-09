package com.service;

import com.dao.PartDAO;
import com.model.Part;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PartServiceImpl implements PartService {

    @Autowired
    private PartDAO partDAO;

    public void setPartDAO(PartDAO partDAO) {
        this.partDAO = partDAO;
    }

    @Override
    @Transactional
    public void addPart(int project, String role, String character) {
        Part p = new Part();
        p.setProject(project);
        p.setRole(role);
        p.setCharacter(character);
        p.setUser(null);
        
        partDAO.addPart(p);
    }

    @Override
    @Transactional
    public void updatePart(int id, String user) {
        Part p = getPartById(id);
        if(p.getUser() == null)
            p.setUser(user);
        
        partDAO.updatePart(p);
    }

    @Override
    @Transactional
    public Part getPartById(int id) {
        return partDAO.getPartById(id);
    }
    
    @Override
    @Transactional
    public List<Part> listPartsByProject(int id) {
        return partDAO.listPartsByProject(id);
    }
}
