
package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "PROGETTO")
public class Project {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "titolo")
    private String title;
    @Column(name = "generi")
    private String genres;
    @Column(name = "trama")
    private String plot;
    @Column(name = "locandina")
    private String img;
    @Column(name = "scadenza")
    private Date deadLine;
    @Column(name = "attuale")
    private double actual;
    @Column(name = "minimo")
    private double min;
    @Column(name = "premi")
    private String prizes;
    @Column(name = "proprietario")
    private String owner;
    //ridondanza da aggiungere al DB
    //private int donations;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getGenres() {
        return genres;
    }

    public String getPlot() {
        return plot;
    }

    public String getImg() {
        return img;
    }

    public Date getDeadLine() {
        return deadLine;
    }

    public double getActual() {
        return actual;
    }

    public double getMin() {
        return min;
    }

    public String getOwner() {
        return owner;
    }

    public String getPrizes() {
        return prizes;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setDeadLine(Date deadLine) {
        this.deadLine = deadLine;
    }

    public void setActual(double actual) {
        this.actual = actual;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setPrizes(String prizes) {
        this.prizes = prizes;
    }
    
    @Override
    public String toString() {
        return "Project{" + "id=" + id + ", title=" + title + ", genres=" + genres + ", plot=" + plot + ", img=" + img + ", deadLine=" + deadLine + ", actual=" + actual + ", min=" + min + ", owner=" + owner + '}';
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
        Project other = (Project) obj;
        return id == other.id;
    }
}
