package one.suhas.rmc.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import one.suhas.rmc.utils.StarValue;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@EntityListeners(AuditingEntityListener.class)
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "rmcClass_id")
    @JsonBackReference
    private RMCClass rmcClass;

    private StarValue interesting;
    private StarValue hard;

    private StarValue homework;

    private StarValue exams;

    @CreatedDate
    private LocalDateTime createdDate;
    public Review(RMCClass rmcClass,  StarValue interesting, StarValue hard, StarValue homework, StarValue exams) {
//        this.id = id;
        this.rmcClass = rmcClass;
        this.interesting = interesting;
        this.hard = hard;
        this.homework = homework;
        this.exams = exams;
    }

    public String toString() {
        return String.format("Review[RMCClass: ID=%d name=%s][h=%s][i=%s][d=%s][e=%s]", rmcClass.getId(), rmcClass.getClassName(), homework.name(), interesting.name(), hard.name(), exams.name());
    }

    public Review() {}

    public Long getId() { return id; }

    public RMCClass getRmcClass() { return rmcClass; }

    public void setRmcClass(RMCClass c) { rmcClass = c; }

    public StarValue getInteresting() { return interesting; }
    public StarValue getHard() { return hard; }
    public StarValue getHomework() { return homework; }

    public StarValue getExams() { return exams; }

    public LocalDateTime getCreatedDate() { return createdDate;}

    public void setInteresting(StarValue n) { interesting = n; }
    public void setHard(StarValue n) { hard = n; }
    public void setHomework(StarValue n) { homework = n; }
    public void setExams(StarValue n) { exams = n; }

    public void setCreatedDate(LocalDateTime d) { createdDate = d;}


}
