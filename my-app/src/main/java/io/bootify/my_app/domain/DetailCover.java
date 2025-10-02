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
public class DetailCover {

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
    private Integer coverId;

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
    private Double pv9;

    @Column
    private Double pv10;

    @Column
    private Double pv11;

    @Column
    private Double pv12;

    @Column
    private Double pv13;

    @Column
    private Double pv14;

    @Column
    private Double pv15;

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
    private Double sv9;

    @Column
    private Double sv10;

    @Column
    private Double sv11;

    @Column
    private Double sv12;

    @Column
    private Double sv13;

    @Column
    private Double sv14;

    @Column
    private Double sv15;

    @Column
    private Double lengthCover;

    @Column
    private Double speed;

    @Column
    private Double weightUsed;

    @Column
    private Double weightError;

    @Column
    private Double u1;

    @Column
    private Double u2;

    @Column
    private Double u3;

    @Column
    private Double i1;

    @Column
    private Double i2;

    @Column
    private Double i3;

    @Column
    private Double cosF1;

    @Column
    private Double cosF2;

    @Column
    private Double cosF3;

    @Column
    private Double p;

    @Column
    private Double ph;

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
