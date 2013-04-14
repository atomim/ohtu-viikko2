/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package olutopas.model;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author olavilin
 */
@Entity
public class User {
    
    @Id
    Integer id;
    
    @OneToMany
    List<Rating> ratings;
    
    private String username;
    
    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public List<Rating> getRatings(){
        return ratings;
    }
    public void setRatings(List<Rating> ratings){
        this.ratings =ratings;
    }

    @Override
    public String toString() {
        return username;
    }
    
}
