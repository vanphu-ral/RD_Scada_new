package io.bootify.my_app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.OffsetDateTime;
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
@Table(name="ScanSerialCheck")
public class ScanSerialCheck {

    @Id
    @Column(nullable = false, updatable = false,name="serialID")
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
    private Long serialId;

    @Column(length = 50,name="serialBoard")
    private String serialBoard;

    @Column(length = 50,name="serialItem")
    private String serialItem;

    @Column(length = 20,name="serialStatus")
    private String serialStatus;

    @Column(length = 20,name="serialCheck")
    private String serialCheck;

    @Column(columnDefinition = "datetime2", name="timeScan")
    private OffsetDateTime timeScan;

    @Column(columnDefinition = "datetime2", name="timeCheck")
    private OffsetDateTime timeCheck;

    @Column(length = 30,name="resultCheck")
    private String resultCheck;

    @Column(length = 20,name="workOrder")
    private String workOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore // Thêm vào tất cả các Set/Collection gây lỗi
    @JoinColumn(name = "machineID")
    private MachinesModels machine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore // Thêm vào tất cả các Set/Collection gây lỗi
    @JoinColumn(name = "productionOrderID")
    private ProductionOrderModels productionOrder;

    @OneToMany(mappedBy = "scanSerialCheck", fetch = FetchType.LAZY)
    @JsonIgnore // Thêm vào tất cả các Set/Collection gây lỗi
    private Set<DetailParamsFCTATE> detailParamsFctate;

}
