package one.suhas.rmc.entity;


import jakarta.persistence.*;
@Entity
public class RMCClass {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String className;

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

    public String getClassName() {
        return className;
    }
}
