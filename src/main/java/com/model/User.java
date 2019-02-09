
package com.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name = "UTENTE")
public class User implements Serializable {

    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "mail")
    private String mail;
    @Column(name = "nome")
    private String name;
    @Column(name = "cognome")
    private String surname;
    @Column(name = "nascita")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date birth;
    @Column(name = "nazione")
    private String nation;
    @Column(name = "ruoli")
    private String roles;
    @Column(name = "immagine")
    private String image;

    public String getId() {
        return id;
    }

    public String getMail() {
        return mail;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Date getBirth() {
        return birth;
    }

    public String getNation() {
        return nation;
    }

    public String getRoles() {
        return roles;
    }

    public String getImage() {
        return image;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public void setImage(String image) {
        this.image = image;
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
        User other = (User) obj;
        return id.equals(other.getId());
    }
}
