
package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "DONAZIONE")
public class Donation {

    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "progetto")
    private int project;
    @Column(name = "utente")
    private String user;
    @Column(name = "somma")
    private double sum;

    public String getId() {
        return id;
    }

    public int getProject() {
        return project;
    }

    public String getUser() {
        return user;
    }

    public double getSum() {
        return sum;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setProject(int project) {
        this.project = project;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Donation other = (Donation) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Donation{" + "id=" + id + ", project=" + project + ", user=" + user + ", sum=" + sum + '}';
    }
}
