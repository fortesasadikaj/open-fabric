package ai.openfabric.api.model.mapper;

import ai.openfabric.api.dto.WorkerDTO;
import ai.openfabric.api.dto.WorkerInformationDTO;
import ai.openfabric.api.model.Worker;

public class WorkerMapper {

    public static WorkerDTO workerToWorkerDTO(Worker worker) {
        return WorkerDTO.builder()
                .containerId(worker.containerId)
                .name(worker.name)
                .port(worker.port)
                .state(worker.state)
                .build();
    }

    public static WorkerInformationDTO workerToWorkerInformationDTO(Worker worker) {
        return WorkerInformationDTO.builder()
                .containerId(worker.containerId)
                .name(worker.name)
                .port(worker.port)
                .state(worker.state)
                .deletedAt(worker.deletedAt)
                .createdAt(worker.createdAt)
                .updatedAt(worker.updatedAt)
                .build();
    }

}
