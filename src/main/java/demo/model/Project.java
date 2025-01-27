package demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Project {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String description;

    @Column(name = "project_month")
    private int projectMonth;

    private int projectYear;

    private String fundingAgency;

    @ManyToOne
    private Person supervisor;

    @ManyToMany
    private List<Person> researchers; // Lista di ricercatori



    public Project(){}

    public Project(String name, String description, int projectMonth, int projectYear, String fundingAgency, Person supervisor, List<Person> researchers, List<WorkLog> workLogs) {
        this.name = name;
        this.description = description;
        this.projectMonth = projectMonth;
        this.projectYear = projectYear;
        this.fundingAgency = fundingAgency;
        this.supervisor = supervisor;
        this.researchers = researchers;
        this.workLogs = workLogs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMonth() {
        return projectMonth;
    }

    public void setMonth(int projectMonth) {
        this.projectMonth = projectMonth;
    }

    public int getYear() {
        return projectYear;
    }

    public void setYear(int year) {
        this.projectYear = year;
    }

    public String getFundingAgency() {
        return fundingAgency;
    }

    public void setFundingAgency(String fundingAgency) {
        this.fundingAgency = fundingAgency;
    }

    public Person getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Person supervisor) {
        this.supervisor = supervisor;
    }

    public List<Person> getResearchers() {
        return researchers;
    }

    public void setResearchers(List<Person> researchers) {
        this.researchers = researchers;
    }

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<WorkLog> workLogs = new ArrayList<>();

    public List<WorkLog> getWorkLogs() {
        return workLogs;
    }

    public void setWorkLogs(List<WorkLog> workLogs) {
        this.workLogs = workLogs;
    }
}
