package ai.openfabric.api.model;


import ai.openfabric.api.model.enums.WorkerState;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity()
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Worker extends Datable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "of-uuid")
    @GenericGenerator(name = "of-uuid", strategy = "ai.openfabric.api.model.IDGenerator")
    @Getter
    @Setter
    public String id;

    public String containerId;

    public String name;

    //
    public Integer port;

    @Enumerated(EnumType.STRING)
    public WorkerState state;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="worker_id")
    public List<WorkerStatistics> statistics;


}
