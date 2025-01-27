package demo.model;

import demo.model.Person;
import demo.model.Project;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class WorkLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDate date;
    private int hours;

    @ManyToOne
    private Person researcher;

    @ManyToOne
    private Project project;

    public WorkLog() {}

    public WorkLog(LocalDate date, int hours, Person researcher, Project project) {
        this.date = date;
        this.hours = hours;
        this.researcher = researcher;
        this.project = project;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public Person getResearcher() {
        return researcher;
    }

    public void setResearcher(Person researcher) {
        this.researcher = researcher;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}

