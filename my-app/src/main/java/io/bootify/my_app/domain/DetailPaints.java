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
public class DetailPaints {

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
    private Integer detailPaintId;

    @Column
    private Double converyorSpeed1;

    @Column
    private Double converyorSpeed2;

    @Column
    private Double speedGun01;

    @Column
    private Double speedGun02;

    @Column
    private Double speedGun03;

    @Column
    private Double speedGun04;

    @Column
    private Double speedGun05;

    @Column
    private Double phArea01;

    @Column
    private Double phArea02;

    @Column
    private Double phArea03;

    @Column
    private Double phArea04;

    @Column
    private Double phArea05;

    @Column
    private Double esdArea01;

    @Column
    private Double esdArea02;

    @Column
    private Double esdArea03;

    @Column
    private Double esdArea04;

    @Column
    private Double esdArea05;

    @Column
    private Double tempArea01;

    @Column
    private Double tempArea02;

    @Column
    private Double tempArea03;

    @Column
    private Double tempArea04;

    @Column
    private Double tempArea05;

    @Column
    private Double tempDrying01;

    @Column
    private Double tempDrying02;

    @Column
    private Double tempDrying03;

    @Column
    private Double tempDrying04;

    @Column
    private Double tempDrying05;

    @Column
    private Double weightPaintAvaiable;

    @Column
    private Double weightPaintUsed;

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
