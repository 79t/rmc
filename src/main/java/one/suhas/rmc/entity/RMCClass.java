package one.suhas.rmc.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Set;

@Entity
public class RMCClass {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String className;


    @OneToMany(mappedBy = "rmcClass")
    @JsonManagedReference
    private Set<Review> reviews;

    @OneToMany(mappedBy = "rmcClass")
    @JsonManagedReference
    private Set<TextReview> textReviews;


    protected RMCClass() {}

    public RMCClass(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return String.format("RMCClass[id=%d, className=%s]", this.id, this.className);
    }

    public Long getId() {
        return id;
    }

    public Set<Review> getReviews() {
        return reviews;
    }
    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public Set<TextReview> getTextReviews() { return textReviews; }
    public void setTextReviews(Set<TextReview> r) { this.textReviews = textReviews; }

    public String getClassName() {
        return className;
    }
}
