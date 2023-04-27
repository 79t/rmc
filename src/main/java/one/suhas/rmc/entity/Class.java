package one.suhas.rmc.entity;


import jakarta.persistence.*;
@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "uniqueCLass", columnNames = {"className"}))
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "className", nullable = false)
    private String className;

    protected Class() {}

    public Class(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return String.format("Class[id=%d, className=%s]", this.id, this.className);
    }

    public Long getId() {
        return id;
    }

    public String getClassName() {
        return className;
    }
}
