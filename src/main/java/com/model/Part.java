
package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PARTE")
public class Part {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "ruolo")
    private String role;
    @Column(name = "personaggio")
    private String character;
    @Column(name = "utente")
    private Integer user;
    @Column(name = "progetto")
    private int project;

    public int getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public String getCharacter() {
        return character;
    }

    public Integer getUser() {
        return user;
    }

    public int getProject() {
        return project;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public void setProject(int project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "Parte{" + "id=" + id + ", role=" + role + ", character=" + character + ", user=" + user + ", project=" + project + '}';
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Part other = (Part) obj;
        return id == other.id;
    }
}
