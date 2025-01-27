package demo;

import demo.model.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;

@Component
public class DataInitializer implements CommandLineRunner {

    private final PersonRepository personRepository;
    private final ProjectRepository projectRepository;

    public DataInitializer(PersonRepository personRepository, ProjectRepository projectRepository) {
        this.personRepository = personRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Creazione persone
        Person admin = new Person("a", "a", "CF12345", "admin", "admin", Role.ADMINISTRATOR);
        Person supervisor = new Person("s", "s", "CF12346", "supervisor", "supervisor", Role.SUPERVISOR);
        Person researcher = new Person("r", "r", "CF12347", "researcher", "researcher", Role.RESEARCHER);

        Person admin1 = new Person("Alice", "Admin", "CF23456", "aliceAdmin", "alice123", Role.ADMINISTRATOR);
        Person supervisor1 = new Person("Bob", "Smith", "CF23457", "bobSupervisor", "bob123", Role.SUPERVISOR);
        Person supervisor2 = new Person("Charlie", "Brown", "CF23458", "charlieSupervisor", "charlie123", Role.SUPERVISOR);
        Person researcher1 = new Person("David", "Johnson", "CF23459", "davidResearcher", "david123", Role.RESEARCHER);
        Person researcher2 = new Person("Eva", "Williams", "CF23460", "evaResearcher", "eva123", Role.RESEARCHER);
        Person researcher3 = new Person("Frank", "Taylor", "CF23461", "frankResearcher", "frank123", Role.RESEARCHER);
        Person researcher4 = new Person("Grace", "Davis", "CF23462", "graceResearcher", "grace123", Role.RESEARCHER);

        // Salvataggio
        personRepository.saveAll(Arrays.asList(admin, supervisor, researcher, admin1, supervisor1, supervisor2, researcher1, researcher2, researcher3, researcher4));

        // Crea progetti con supervisore e ricercatori, inclusa la descrizione
        Project project1 = new Project(
                "Project 1: AI Research",
                "This project aims to advance AI research in the field of natural language processing.",
                1,
                2024,
                "Agency A",
                supervisor,
                Arrays.asList(researcher1, researcher2,researcher),
                Collections.emptyList() //Worklogs vuoti
        );

        Project project2 = new Project(
                "Project 2: Space Exploration",
                "Exploring the potential for human habitation on Mars. This project will include robotic space exploration missions.",
                2,
                2024,
                "Agency A",
                supervisor2,
                Arrays.asList(researcher3, researcher4,researcher),
                Collections.emptyList() //Worklogs vuoti
        );

        Project project3 = new Project(
                "Project 3: Renewable Energy Solutions",
                "This project focuses on finding innovative renewable energy solutions for sustainable development.",
                3,
                2024,
                "Agency C",
                supervisor1,
                Arrays.asList(researcher1, researcher3,researcher),
                Collections.emptyList() //Worklogs vuoti
        );

        Project project4 = new Project(
                "Project 4: Climate Change Analysis",
                "Analyzing the long-term effects of climate change on global weather patterns.",
                4,
                2024,
                "Agency D",
                supervisor2,
                Arrays.asList(researcher2, researcher4,researcher),
                Collections.emptyList() //Worklogs vuoti
        );

        // Salvo
        projectRepository.saveAll(Arrays.asList(project1, project2, project3, project4));
    }
}
