package io.bootify.my_app.domain;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Table(name = "MachinesModels")
public class MachinesModels {

    @Id
    @Column(nullable = false, updatable = false,name="MachineID")
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Integer machineId;

    @Column(length = 25,name="MachineName")
    private String machineName;

    @Column(name="StageID")
    private Integer stageId;

    @OneToMany(mappedBy = "machine")
    private Set<DetailProcessing> machineDetailProcessings = new HashSet<>();

    @OneToMany(mappedBy = "machine")
    private Set<LedwireProcessing> machineLedwireProcessings = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MachineGroupID")
    private MachineTypesModels machineGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LineID")
    private LineProductionsModels line;

    @OneToMany(mappedBy = "machine")
    private Set<ScanSerialCheck> machineScanSerialChecks = new HashSet<>();


}
