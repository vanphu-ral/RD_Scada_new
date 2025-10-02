package io.bootify.my_app.domain;

import jakarta.persistence.*;

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
@Table(name="ErrorModel")
public class ErrorModel {

    @Id
    @Column(nullable = false, updatable = false,name="ErrorID")
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
    private Integer errorId;

    @Column(length = 50,name="ErrorName")
    private String errorName;

    @Column(name="StageID")
    private Integer stageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HMIErrID")
    private Hmierror hmierr;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProductTypeGroupID")
    private ProductTypeGroup productTypeGroup;


}
