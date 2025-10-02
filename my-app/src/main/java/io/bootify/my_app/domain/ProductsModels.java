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
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
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
public class ProductsModels {

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
    private Integer productId;

    @Column(length = 20)
    private String productCode;

    @Column(length = 100)
    private String productName;

    @Column(length = 50)
    private String descriptions;

    @OneToMany(mappedBy = "product")
    private Set<ParameterModels> productParameterModelses = new HashSet<>();

    @OneToMany(mappedBy = "product")
    private Set<ParameterPaints> productParameterPaintses = new HashSet<>();

    @OneToMany(mappedBy = "product")
    private Set<ProductionOrderModels> productProductionOrderModelses = new HashSet<>();

    @OneToMany(mappedBy = "product")
    private Set<ProductProfile> productProductProfiles = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_type_group_id")
    private ProductTypeGroup productTypeGroup;

    @OneToMany(mappedBy = "product")
    private Set<ProfileTem> productProfileTems = new HashSet<>();

    @CreatedDate
    @Column(nullable = false, updatable = false, columnDefinition = "datetime2")
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false, columnDefinition = "datetime2")
    private OffsetDateTime lastUpdated;

}
