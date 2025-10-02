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
@Table(name = "ProductionOrderModels")
public class ProductionOrderModels {

    @Id
    @Column(nullable = false, updatable = false, name="ProductionOrderId")
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
    private Integer productionOrderId;

    @Column(length = 25,name="WorkOrder")
    private String workOrder;

    @Column(length = 25,name="Working")
    private Integer working;

    @Column(name = "Number_Of_Plan")
    private Integer numberOfPlan;

    @Column(name = "Lot" )
    private String lot;

    @Column(name = "Alert1")
    private Integer alert1;

    @Column(name = "Alert2")
    private Integer alert2;


    @Column(name = "Alert3")
    private Integer alert3;

    @Column(name = "Alert4")
    private Integer alert4;

    @Column(name = "Alert5")
    private Integer alert5;

    @Column (name = "Alert6")
    private Integer alert6;

    @Column (name = "Alert7")
    private Integer alert7;

    @OneToMany(mappedBy = "productionOrder")
    private Set<DetailCover> productionOrderDetailCovers = new HashSet<>();

    @OneToMany(mappedBy = "productionOrder")
    private Set<DetailLabel> productionOrderDetailLabels = new HashSet<>();

    @OneToMany(mappedBy = "productionOrder")
    private Set<DetailLuyen> productionOrderDetailLuyens = new HashSet<>();

    @OneToMany(mappedBy = "productionOrder")
    private Set<DetailPaints> productionOrderDetailPaintses = new HashSet<>();

    @OneToMany(mappedBy = "productionOrder")
    private Set<DetailProcessing> productionOrderDetailProcessings = new HashSet<>();

    @OneToMany(mappedBy = "productionOrder")
    private Set<DetailTestLight> productionOrderDetailTestLights = new HashSet<>();

    @OneToMany(mappedBy = "productionOrder")
    private Set<LedwireProcessing> productionOrderLedwireProcessings = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MachineGroupID")
    private MachineTypesModels machineGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProductID")
    private ProductsModels product;

    @OneToMany(mappedBy = "order")
    private Set<ScanSerial> orderScanSerials = new HashSet<>();

    @OneToMany(mappedBy = "productionOrder")
    private Set<ScanSerialCheck> productionOrderScanSerialChecks = new HashSet<>();


}
