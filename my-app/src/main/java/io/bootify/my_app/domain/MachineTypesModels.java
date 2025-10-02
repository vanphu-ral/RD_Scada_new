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
@Table(name="MachineTypesModels")
public class MachineTypesModels {

    @Id
    @Column(nullable = false, updatable = false,name="MachineGroupID")
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
    private Integer machineGroupId;

    @Column(length = 25,name="MachineGroupName")
    private String machineGroupName;

    @Column(length = 100,name="Descriptions")
    private String descriptions;

    @OneToMany(mappedBy = "machineGroup")
    private Set<MachinesModels> machineGroupMachinesModelses = new HashSet<>();

    @OneToMany(mappedBy = "machineGroup")
    private Set<ProductionOrderModels> machineGroupProductionOrderModelses = new HashSet<>();


}
