package io.bootify.my_app.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class DetailProcessing {

    @Id
    @Column(nullable = false, updatable = false)
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
    private Long id;

    @Column
    private Integer machineStatus;

    @Column
    private Integer machineWorking;

    @Column
    private Integer numberOfInput;

    @Column
    private Integer numberOfOutput;

    @Column
    private Integer runningTime;

    @Column
    private Integer stoppingTime;

    @Column
    private Integer cycleTime;

    @Column
    private Integer e1;

    @Column
    private Integer e2;

    @Column
    private Integer e3;

    @Column
    private Integer e4;

    @Column
    private Integer e5;

    @Column
    private Integer e6;

    @Column
    private Integer e7;

    @Column
    private Integer e8;

    @Column
    private Integer e9;

    @Column
    private Integer e10;

    @Column
    private Integer e11;

    @Column
    private Integer e12;

    @Column
    private Integer e13;

    @Column
    private Integer e14;

    @Column
    private Integer e15;

    @Column
    private Integer e16;

    @Column
    private Integer e17;

    @Column
    private Integer e18;

    @Column
    private Integer e19;

    @Column
    private Integer e20;

    @Column(length = 20)
    private String hmi;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_id")
    private MachinesModels machine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "production_order_id")
    private ProductionOrderModels productionOrder;

    @CreatedDate
    @Column(nullable = false, updatable = false, columnDefinition = "datetime2")
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false, columnDefinition = "datetime2")
    private OffsetDateTime lastUpdated;

}
