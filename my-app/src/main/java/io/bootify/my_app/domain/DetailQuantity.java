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
@Table(name = "DetailQuantity")
public class DetailQuantity {

    @Id
    @Column(nullable = false, updatable = false,name = "detailQID")
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
    private Long detailQid;

    @Column(length = 20,name = "workOrder")
    private String workOrder;

    @Column(length = 30,name="machineName")
    private String machineName;

    @Column(name="numberInput")
    private Integer numberInput;

    @Column(name="numberOutput")
    private Integer numberOutput;

    @Column(name="runTime")
    private Integer runTime;

    @Column(name="stoptime")
    private Integer stoptime;

    @Column(columnDefinition = "datetime2",name="infoDay")
    private OffsetDateTime infoDay;

}
