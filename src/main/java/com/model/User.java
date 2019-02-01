
package com.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name = "UTENTE")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "mail")
    private String mail;
    @Column(name = "telefono")
    private String phone;
    @Column(name = "nome")
    private String name;
    @Column(name = "cognome")
    private String surname;
    @Column(name = "nascita")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date birth;
    @Column(name = "indirizzo")
    private String address;
    @Column(name = "ruoli")
    private String roles;
    @Column(name = "immagine")
    private String image;
    @Column(name = "credito")
    private String credit;

    public int getId() {
        return id;
    }

    public String getMail() {
        return mail;
    }

    public String getPhone() {
        return phone;
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

    public String getAddress() {
        return address;
    }

    public String getRoles() {
        return roles;
    }

    public String getImage() {
        return image;
    }

    public String getCredit() {
        return credit;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public void setAddress(String address) {
        this.address = address;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    @Override
    public String toString() {
        return "Utente{" + "id=" + id + ", mail=" + mail + ", phone=" + phone + ", name=" + name + ", surname=" + surname + ", birth=" + birth + ", address=" + address + ", roles=" + roles + ", image=" + image + ", credit=" + credit + '}';
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
        User other = (User) obj;
        return id == other.id;
    }
}
