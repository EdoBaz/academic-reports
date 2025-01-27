package demo.unitTests;

import static org.junit.Assert.*;

import demo.model.Person;
import demo.model.Project;
import demo.model.Role;
import demo.model.WorkLog;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;



public class WorkLogTest {

    private WorkLog workLog;
    private Person researcher;
    private Project project;

    @Before
    public void setUp() {
        researcher = new Person("Researcher", "One", "RES123", "researcher1", "password1", Role.RESEARCHER);
        project = new Project();
        workLog = new WorkLog(LocalDate.of(2025, 1, 10), 8, researcher, project);
    }

    @Test
    public void testConstructor() {
        assertEquals(LocalDate.of(2025, 1, 10), workLog.getDate());
        assertEquals(8, workLog.getHours());
        assertEquals(researcher, workLog.getResearcher());
        assertEquals(project, workLog.getProject());
    }

    @Test
    public void testSetAndGetDate() {
        workLog.setDate(LocalDate.of(2025, 1, 11));
        assertEquals(LocalDate.of(2025, 1, 11), workLog.getDate());
    }

    @Test
    public void testSetAndGetHours() {
        workLog.setHours(10);
        assertEquals(10, workLog.getHours());
    }

    @Test
    public void testSetAndGetResearcher() {
        Person newResearcher = new Person("Researcher", "Two", "RES456", "researcher2", "password2", Role.RESEARCHER);
        workLog.setResearcher(newResearcher);
        assertEquals(newResearcher, workLog.getResearcher());
    }

    @Test
    public void testSetAndGetProject() {
        Project newProject = new Project();
        workLog.setProject(newProject);
        assertEquals(newProject, workLog.getProject());
    }
}
