package ai.openfabric.api.dto;

import ai.openfabric.api.model.enums.WorkerState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkerDTO {

    public String containerId;

    public String name;
    public Integer port;

    public WorkerState state;

}
