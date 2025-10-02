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
public class CheckTem {

    @Id
    @Column(nullable = false, updatable = false,name = "checkId")
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
    private Long checkId;

    @Column(name ="poName" )
    private String poName;

    @Column(name = "passQLCL")
    private String passQlcl;

    @Column(name = "ngQLCL")
    private String ngQlcl;

    @Column(name = "userQLCL")
    private String userQlcl;

    @Column(name = "passBranch")
    private String passBranch;

    @Column(name = "ngBranch")
    private String ngBranch;

    @Column(name = "userBranch")
    private String userBranch;

    @Column(columnDefinition = "datetime2",name = "checkedDate")
    private OffsetDateTime checkedDate;

    @Column(length = 50,name = "statusQLCL")
    private String statusQlcl;

    @Column(length = 50,name = "statusBranch")
    private String statusBranch;

    @Column(length = 50,name = "totals")
    private String totals;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profileId")
    private ProfileTem profile;





}
