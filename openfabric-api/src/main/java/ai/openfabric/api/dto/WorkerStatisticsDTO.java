package ai.openfabric.api.dto;

import ai.openfabric.api.model.enums.WorkerState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkerStatisticsDTO {
    private WorkerState workerState;
    public Date createdAt;
    public Date updatedAt;
    public Date deletedAt;

}
