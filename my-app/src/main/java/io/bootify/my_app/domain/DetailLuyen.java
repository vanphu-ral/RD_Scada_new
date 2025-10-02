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
public class DetailLuyen {

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
    private Double ul1min;

    @Column
    private Double ul2min;

    @Column
    private Double ul3min;

    @Column
    private Double ul4min;

    @Column
    private Double ul5min;

    @Column
    private Double ul6min;

    @Column
    private Double ul7min;

    @Column
    private Double ul8min;

    @Column
    private Double ul1max;

    @Column
    private Double ul2max;

    @Column
    private Double ul3max;

    @Column
    private Double ul4max;

    @Column
    private Double ul5max;

    @Column
    private Double ul6max;

    @Column
    private Double ul7max;

    @Column
    private Double ul8max;

    @Column
    private Double u1;

    @Column
    private Double u2;

    @Column
    private Double u3;

    @Column
    private Double u4;

    @Column
    private Double u5;

    @Column
    private Double u6;

    @Column
    private Double u7;

    @Column
    private Double u8;

    @Column
    private Double pv1;

    @Column
    private Double pv2;

    @Column
    private Double pv3;

    @Column
    private Double pv4;

    @Column
    private Double pv5;

    @Column
    private Double pv6;

    @Column
    private Double pv7;

    @Column
    private Double pv8;

    @Column
    private Double sv1;

    @Column
    private Double sv2;

    @Column
    private Double sv3;

    @Column
    private Double sv4;

    @Column
    private Double sv5;

    @Column
    private Double sv6;

    @Column
    private Double sv7;

    @Column
    private Double sv8;

    @Column
    private Double speed1;

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
