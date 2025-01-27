package demo.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface WorkLogRepository extends JpaRepository<WorkLog, Long> {
    WorkLog findByResearcherAndProjectAndDate(Person researcher, Project project, LocalDate date);
    List<WorkLog> findByResearcherAndProject(Person user, Project project);
    List<WorkLog> findByProjectId(Long projectId);
    List<WorkLog> findByResearcher(Person researcher);
    List<WorkLog> findByResearcherAndDate(Person researcher, LocalDate date);

}

