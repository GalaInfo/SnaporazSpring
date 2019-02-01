
package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DONAZIONE")
public class Donation {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "somma")
    private int sum;
    @Column(name = "donatore")
    private int donor;
    @Column(name = "progetto")
    private int project;

    public int getId() {
        return id;
    }

    public int getSum() {
        return sum;
    }

    public int getDonor() {
        return donor;
    }

    public int getProject() {
        return project;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public void setDonor(int donor) {
        this.donor = donor;
    }

    public void setProject(int project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "Donation{" + "id=" + id + ", sum=" + sum + ", donor=" + donor + ", project=" + project + '}';
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
        Donation other = (Donation) obj;
        return id == other.id;
    }
}
