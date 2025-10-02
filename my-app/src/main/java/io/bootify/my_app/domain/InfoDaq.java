package io.bootify.my_app.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class InfoDaq {

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
    private Long recordId;

    @Column(length = 20)
    private String deviceName;

    @Column(length = 20)
    private String profileName;

    @Column(length = 20)
    private String leftRight;

    @Column
    private Integer fixtureNo;

    @Column(columnDefinition = "datetime2")
    private OffsetDateTime startTime;

    @Column(columnDefinition = "datetime2")
    private OffsetDateTime endTime;

    @Column(length = 20)
    private String elapsedTime;

    @Column(length = 10)
    private String resultTest;

    @Column(length = 10)
    private String inputVoltage1;

    @Column(length = 10)
    private String inputFrequency1;

    @Column(length = 10)
    private String pInAMin1;

    @Column(length = 10)
    private String pInAMax1;

    @Column(length = 10)
    private String vInMin1;

    @Column(length = 10)
    private String vInMax1;

    @Column(length = 10)
    private String iInMin1;

    @Column(length = 10)
    private String iInMax1;

    @Column(length = 10)
    private String pInWMin1;

    @Column(length = 10)
    private String pInWMax1;

    @Column(length = 10)
    private String pFMin1;

    @Column(length = 10)
    private String pFMax1;

    @Column(length = 10)
    private String pPrev1;

    @Column(length = 10)
    private String vInPrev1;

    @Column(length = 10)
    private String iInPrev1;

    @Column(length = 10)
    private String pInPrev1;

    @Column(length = 10)
    private String pFPrev1;

    @Column(length = 10)
    private String inputVoltage2;

    @Column(length = 10)
    private String inputFrequency2;

    @Column(length = 10)
    private String pInAMin2;

    @Column(length = 10)
    private String pInAMax2;

    @Column(length = 10)
    private String vInMin2;

    @Column(length = 10)
    private String vInMax2;

    @Column(length = 10)
    private String iInMin2;

    @Column(length = 10)
    private String iInMax2;

    @Column(length = 10)
    private String pInWMin2;

    @Column(length = 10)
    private String pInWMax2;

    @Column(length = 10)
    private String pFMin2;

    @Column(length = 10)
    private String pFMax2;

    @Column(length = 10)
    private String pPrev2;

    @Column(length = 10)
    private String vInPrev2;

    @Column(length = 10)
    private String iInPrev2;

    @Column(length = 10)
    private String pInPrev2;

    @Column(length = 10)
    private String pFPrev2;

    @CreatedDate
    @Column(nullable = false, updatable = false, columnDefinition = "datetime2")
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false, columnDefinition = "datetime2")
    private OffsetDateTime lastUpdated;

}
