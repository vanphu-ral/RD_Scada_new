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
public class ParameterPaints {

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
    private Integer idparamsPaint;

    @Column
    private Double setConveryorSpeed1;

    @Column
    private Double setConveryorSpeed2;

    @Column
    private Double setSpeedGun01;

    @Column
    private Double setSpeedGun02;

    @Column
    private Double setSpeedGun03;

    @Column
    private Double setSpeedGun04;

    @Column
    private Double setSpeedGun05;

    @Column
    private Double setPhArea01;

    @Column
    private Double setPhArea02;

    @Column
    private Double setPhArea03;

    @Column
    private Double setPhArea04;

    @Column
    private Double setPhArea05;

    @Column
    private Double setEsdArea01;

    @Column
    private Double setEsdArea02;

    @Column
    private Double setEsdArea03;

    @Column
    private Double setEsdArea04;

    @Column
    private Double setEsdArea05;

    @Column
    private Double setTempArea01;

    @Column
    private Double setTempArea02;

    @Column
    private Double setTempArea03;

    @Column
    private Double setTempArea04;

    @Column
    private Double setTempArea05;

    @Column
    private Double setTempDrying01;

    @Column
    private Double setTempDrying02;

    @Column
    private Double setTempDrying03;

    @Column
    private Double setTempDrying04;

    @Column
    private Double setTempDrying05;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductsModels product;

    @CreatedDate
    @Column(nullable = false, updatable = false, columnDefinition = "datetime2")
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false, columnDefinition = "datetime2")
    private OffsetDateTime lastUpdated;

}
