package ai.openfabric.api.config;

import ai.openfabric.api.model.Worker;
import ai.openfabric.api.model.enums.WorkerState;
import ai.openfabric.api.repository.WorkerRepository;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class WorkerInit {

    @Autowired
    private DockerClient dockerClient;

    @Autowired
    private WorkerRepository workerRepository;

    @PostConstruct
    public void initWorkers() {
        List<Container> containers = dockerClient.listContainersCmd().exec();
        for(Container container : containers) {

            Worker worker = workerRepository.findByContainerId(container.getId())
                    .orElse(Worker.builder().build());

            worker.setContainerId(container.getId());
            worker.setName(container.getNames()[0]);
            worker.setPort(container.getPorts()[0].getPublicPort());
            worker.setState(WorkerState.valueOf(container.getState().toUpperCase()));

            workerRepository.save(worker);
        }

    }
}
