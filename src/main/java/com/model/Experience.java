
package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "ESPERIENZA")
public class Experience {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "titolo")
    private String title;
    @Column(name = "generi")
    private String genres;
    @Column(name = "ruolo")
    private String role;
    @Column(name = "personaggio")
    private String character;
    @Column(name = "inizio")
    private Date start;
    @Column(name = "fine")
    private Date end;
    @Column(name = "utente")
    private int user;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getGenres() {
        return genres;
    }

    public String getRole() {
        return role;
    }

    public String getCharacter() {
        return character;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public int getUser() {
        return user;
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

    public void setRole(String role) {
        this.role = role;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public void setUser(int user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Experience{" + "id=" + id + ", title=" + title + ", genres=" + genres + ", role=" + role + ", character=" + character + ", start=" + start + ", end=" + end + ", user=" + user + '}';
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
        Experience other = (Experience) obj;
        return id == other.id;
    }
}
