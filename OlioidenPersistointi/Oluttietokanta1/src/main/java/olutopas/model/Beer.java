package olutopas.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Beer {

    private String name;
    @Id
    private Integer id;
    @ManyToOne
    private Brewery brewery;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Rating> ratings;

    public Beer() {
    }

    public Beer(String name) {
        this.name = name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Brewery getBrewery() {
        return brewery;
    }

    public void setBrewery(Brewery brewery) {
        this.brewery = brewery;
    }

    @Override
    public String toString() {
        // olioiden kannattaa sisäisestikin käyttää gettereitä oliomuuttujien sijaan
        // näin esim. olueeseen liittyvä panimo tulee varmasti ladattua kannasta
        String s= getName() + " (" + getBrewery().getName() + ")";
        List<Rating> ratings;
        ratings=this.getRatings();
        if(ratings.size()!=0){
            s+="\n  ratings given "+ratings.size()+" average "+ratingsAvg(ratings);
        }else{
            s+="\n  no ratings!";
        }
        return s; 
    }

    public void addRating(Rating b) {
        if (ratings == null) {
            ratings = new ArrayList<Rating>();
        }

        ratings.add(b);
        b.setBeer(this);
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    private static double ratingsAvg(List<Rating> ratings) {
        int sum = 0;
        for (Rating rating : ratings) {
            sum += rating.getValue();
        }
        return (double) sum / ratings.size();
    }
}
