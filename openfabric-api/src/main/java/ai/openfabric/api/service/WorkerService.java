package ai.openfabric.api.service;

import ai.openfabric.api.dto.WorkerDTO;
import ai.openfabric.api.dto.WorkerInformationDTO;
import ai.openfabric.api.dto.WorkerStatisticsDTO;
import ai.openfabric.api.model.Worker;
import ai.openfabric.api.model.WorkerStatistics;
import ai.openfabric.api.model.enums.WorkerState;
import ai.openfabric.api.model.mapper.WorkerMapper;
import ai.openfabric.api.model.mapper.WorkerStatisticsMapper;
import ai.openfabric.api.repository.WorkerRepository;
import ai.openfabric.api.repository.WorkerStatisticsRepository;
import com.github.dockerjava.api.DockerClient;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WorkerService {

    private final WorkerRepository workerRepository;
    private final WorkerStatisticsRepository workerStatisticsRepository;
    private final DockerClient dockerClient;

    public Page<Worker> getAllWorkers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return workerRepository.findAll(pageable);
    }

    public WorkerDTO changeWorkerState(String containerId, WorkerState workerState) {

        Worker worker = workerRepository.findByContainerId(containerId)
                .orElseThrow(EntityNotFoundException::new);

        worker.setState(workerState);
        workerRepository.save(worker);

        WorkerStatistics workerStatistics = WorkerStatistics.builder()
                .workerState(workerState)
                .worker(worker)
                .build();

        workerStatisticsRepository.save(workerStatistics);

        try {
            changeContainerState(containerId, workerState);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return WorkerMapper.workerToWorkerDTO(worker);
    }

    public void changeContainerState(String containerId, WorkerState workerState) throws Exception {
        if (workerState.equals(WorkerState.PAUSED)) {
            dockerClient.pauseContainerCmd(containerId).exec();
        } else if (workerState.equals(WorkerState.RUNNING)) {
            dockerClient.unpauseContainerCmd(containerId).exec();
        } else {
            throw new Exception("This state does not exists");
        }
    }

    public WorkerInformationDTO getWorkerInformation(String workerId) {

        Worker worker = workerRepository.findById(workerId).orElseThrow(EntityNotFoundException::new);
        return WorkerMapper.workerToWorkerInformationDTO(worker);

    }

    public List<WorkerStatisticsDTO> getWorkerStatistics(String workerId) {

        List<WorkerStatistics> workerStatistics = workerStatisticsRepository.findAllByWorkerId(workerId);
        return workerStatistics.stream().map(WorkerStatisticsMapper::workerStatisticsToWorkerStatisticsDTO).collect(Collectors.toList());

    }
}
