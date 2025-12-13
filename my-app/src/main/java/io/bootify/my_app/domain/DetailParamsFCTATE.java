package io.bootify.my_app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "DetailParamsFCTATE")
public class DetailParamsFCTATE {

    @Id
    @Column(name = "paramsID")
    private Long paramsID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "serialID") // 'serialID' là khóa ngoại
    @JsonIgnore
    private ScanSerialCheck scanSerialCheck;

    @Column(name = "programName")
    private String programName;

    @Column(name = "fixLR")
    private String fixLR;

    @Column(name = "fixID")
    private Integer fixID;

    @Column(name = "startTestTime")
    private LocalDateTime startTestTime;

    @Column(name = "endTestTime")
    private LocalDateTime endTestTime;

    @Column(name = "timeElapsed")
    private String timeElapsed;

    @Column(name = "results")
    private String results;

    @Column(name = "detailParams")
    private String detailParams;
}
