package ai.openfabric.api.controller;

import ai.openfabric.api.dto.WorkerDTO;
import ai.openfabric.api.dto.WorkerInformationDTO;
import ai.openfabric.api.dto.WorkerStatisticsDTO;
import ai.openfabric.api.model.Worker;
import ai.openfabric.api.model.enums.WorkerState;
import ai.openfabric.api.service.WorkerService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${node.api.path}/worker")
@AllArgsConstructor
public class WorkerController {

    private final WorkerService workerService;

    @GetMapping()
    public Page<Worker> getAllWorkers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return workerService.getAllWorkers(page, size);
    }

    @PutMapping("/changeState")
    public WorkerDTO changeWorkerStatus(
            @RequestParam(name = "containerId") String containerId,
            @RequestParam(name = "workerState") WorkerState workerState) throws Exception {
        return workerService.changeWorkerState(containerId, workerState);
    }

    @GetMapping("/worker-information/{workerId}")
    public WorkerInformationDTO getWorkerInformation(@PathVariable(name = "workerId") String workerId) {
        return workerService.getWorkerInformation(workerId);
    }

    @GetMapping("/worker-statistics/{workerId}")
    public List<WorkerStatisticsDTO> getWorkerStatistics(@PathVariable(name = "workerId") String workerId) {
        return workerService.getWorkerStatistics(workerId);
    }
}
