package demo.unitTests;

import demo.model.Person;
import demo.model.Project;
import static org.junit.Assert.*;

import demo.model.Role;
import demo.model.WorkLog;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProjectTest {

    private Project project;
    private Person supervisor;
    private List<Person> researchers;

    @Before
    public void setUp() {
        supervisor = new Person("Supervisor", "Name", "SUP123", "supervisor", "password", Role.ADMINISTRATOR);
        researchers = new ArrayList<>();
        researchers.add(new Person("Researcher", "One", "RES123", "researcher1", "password1", Role.RESEARCHER));
        researchers.add(new Person("Researcher", "Two", "RES456", "researcher2", "password2", Role.RESEARCHER));

        project = new Project("Project Name", "Description", 1, 2025, "Funding Agency", supervisor, researchers, new ArrayList<>());
    }

    @Test
    public void testConstructor() {
        assertEquals("Project Name", project.getName());
        assertEquals("Description", project.getDescription());
        assertEquals(1, project.getMonth());
        assertEquals(2025, project.getYear());
        assertEquals("Funding Agency", project.getFundingAgency());
        assertEquals(supervisor, project.getSupervisor());
        assertEquals(researchers, project.getResearchers());
        assertTrue(project.getWorkLogs().isEmpty());
    }

    @Test
    public void testSetAndGetName() {
        project.setName("New Project Name");
        assertEquals("New Project Name", project.getName());
    }

    @Test
    public void testSetAndGetDescription() {
        project.setDescription("New Description");
        assertEquals("New Description", project.getDescription());
    }

    @Test
    public void testSetAndGetMonth() {
        project.setMonth(2);
        assertEquals(2, project.getMonth());
    }

    @Test
    public void testSetAndGetYear() {
        project.setYear(2026);
        assertEquals(2026, project.getYear());
    }

    @Test
    public void testSetAndGetFundingAgency() {
        project.setFundingAgency("New Funding Agency");
        assertEquals("New Funding Agency", project.getFundingAgency());
    }

    @Test
    public void testSetAndGetSupervisor() {
        Person newSupervisor = new Person("New", "Supervisor", "NEW123", "newsupervisor", "newpassword", Role.ADMINISTRATOR);
        project.setSupervisor(newSupervisor);
        assertEquals(newSupervisor, project.getSupervisor());
    }

    @Test
    public void testSetAndGetResearchers() {
        List<Person> newResearchers = new ArrayList<>();
        newResearchers.add(new Person("Researcher", "Three", "RES789", "researcher3", "password3", Role.RESEARCHER));
        project.setResearchers(newResearchers);
        assertEquals(newResearchers, project.getResearchers());
    }

    @Test
    public void testSetAndGetWorkLogs() {
        List<WorkLog> newWorkLogs = new ArrayList<>();
        newWorkLogs.add(new WorkLog(LocalDate.of(2025, 1, 10), 4, supervisor, project));
        project.setWorkLogs(newWorkLogs);
        assertEquals(newWorkLogs, project.getWorkLogs());
    }
}