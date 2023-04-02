package ai.openfabric.api.model.mapper;

import ai.openfabric.api.dto.WorkerStatisticsDTO;
import ai.openfabric.api.model.WorkerStatistics;

public class WorkerStatisticsMapper {

    public static WorkerStatisticsDTO workerStatisticsToWorkerStatisticsDTO(WorkerStatistics workerStatistics) {
        return WorkerStatisticsDTO.builder()
                .workerState(workerStatistics.getWorkerState())
                .deletedAt(workerStatistics.deletedAt)
                .createdAt(workerStatistics.createdAt)
                .updatedAt(workerStatistics.updatedAt)
                .build();
    }

}
