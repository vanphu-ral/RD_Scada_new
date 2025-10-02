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
@Table(name = "DetailError")
public class DetailError {

    @Id
    @Column(nullable = false, updatable = false,name="detailEID")
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
    private Long detailEid;

    @Column(length = 20,name= "workOrder")
    private String workOrder;

    @Column(length = 30,name="machineName")
    private String machineName;

    @Column(length = 100,name="errorName")
    private String errorName;

    @Column(columnDefinition = "datetime2",name="createdTime")
    private OffsetDateTime createdTime;
}
