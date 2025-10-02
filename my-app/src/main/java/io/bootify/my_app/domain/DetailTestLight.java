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
public class DetailTestLight {

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

    @Column(length = 20)
    private String devicesId;

    @Column
    private Integer productId;

    @Column
    private Integer total;

    @Column
    private Integer pass;

    @Column
    private Integer ng;

    @Column
    private Integer errorU;

    @Column
    private Integer errorI;

    @Column
    private Integer errorP;

    @Column
    private Integer errorpF;

    @Column
    private Integer ch;

    @Column
    private Double u;

    @Column
    private Double i;

    @Column
    private Double p;

    @Column
    private Double pF;

    @Column
    private Double f;

    @Column(length = 20)
    private String result;

    @Column(columnDefinition = "datetime2")
    private OffsetDateTime startTime;

    @Column(columnDefinition = "datetime2")
    private OffsetDateTime endTime;

    @Column(length = 50)
    private String result1;

    @Column(length = 50)
    private String result2;

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
