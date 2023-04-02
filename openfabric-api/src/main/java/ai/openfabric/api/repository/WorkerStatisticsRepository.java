package ai.openfabric.api.repository;

import ai.openfabric.api.model.WorkerStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkerStatisticsRepository extends JpaRepository<WorkerStatistics, String> {

    List<WorkerStatistics> findAllByWorkerId(String workerId);

}
