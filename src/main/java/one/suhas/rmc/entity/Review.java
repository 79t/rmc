package one.suhas.rmc.entity;

import jakarta.persistence.*;
import one.suhas.rmc.enums.StarValue;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private RMCClass rmcClass;

    private StarValue interesting;
    private StarValue hard;

    private StarValue homework;

    private StarValue exams;

    public Review(long id, RMCClass rmcClass, StarValue interesting, StarValue hard, StarValue homework, StarValue exams) {
        this.id = id;
        this.rmcClass = rmcClass;
        this.interesting = interesting;
        this.hard = hard;
        this.homework = homework;
        this.exams = exams;
    }

    public Review() {}

    public Long getId() { return id; }

    public RMCClass getRmcClass() { return rmcClass; }

    public void setRmcClass(RMCClass c) { rmcClass = c; }

    public StarValue getInteresting() { return interesting; }
    public StarValue getHard() { return hard; }
    public StarValue getHomework() { return homework; }

    public StarValue getExams() { return exams; }

    public void setInteresting(StarValue n) { interesting = n; }
    public void setHard(StarValue n) { hard = n; }
    public void setHomework(StarValue n) { homework = n; }
    public void setExams(StarValue n) { exams = n; }



}
