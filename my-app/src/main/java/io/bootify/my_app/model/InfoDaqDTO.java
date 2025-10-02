package io.bootify.my_app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class InfoDaqDTO {

    private Long recordId;

    @Size(max = 20)
    private String deviceName;

    @Size(max = 20)
    private String profileName;

    @Size(max = 20)
    private String leftRight;

    private Integer fixtureNo;

    private OffsetDateTime startTime;

    private OffsetDateTime endTime;

    @Size(max = 20)
    private String elapsedTime;

    @Size(max = 10)
    private String resultTest;

    @Size(max = 10)
    private String inputVoltage1;

    @Size(max = 10)
    private String inputFrequency1;

    @Size(max = 10)
    @JsonProperty("pInAMin1")
    private String pInAMin1;

    @Size(max = 10)
    @JsonProperty("pInAMax1")
    private String pInAMax1;

    @Size(max = 10)
    @JsonProperty("vInMin1")
    private String vInMin1;

    @Size(max = 10)
    @JsonProperty("vInMax1")
    private String vInMax1;

    @Size(max = 10)
    @JsonProperty("iInMin1")
    private String iInMin1;

    @Size(max = 10)
    @JsonProperty("iInMax1")
    private String iInMax1;

    @Size(max = 10)
    @JsonProperty("pInWMin1")
    private String pInWMin1;

    @Size(max = 10)
    @JsonProperty("pInWMax1")
    private String pInWMax1;

    @Size(max = 10)
    @JsonProperty("pFMin1")
    private String pFMin1;

    @Size(max = 10)
    @JsonProperty("pFMax1")
    private String pFMax1;

    @Size(max = 10)
    @JsonProperty("pPrev1")
    private String pPrev1;

    @Size(max = 10)
    @JsonProperty("vInPrev1")
    private String vInPrev1;

    @Size(max = 10)
    @JsonProperty("iInPrev1")
    private String iInPrev1;

    @Size(max = 10)
    @JsonProperty("pInPrev1")
    private String pInPrev1;

    @Size(max = 10)
    @JsonProperty("pFPrev1")
    private String pFPrev1;

    @Size(max = 10)
    private String inputVoltage2;

    @Size(max = 10)
    private String inputFrequency2;

    @Size(max = 10)
    @JsonProperty("pInAMin2")
    private String pInAMin2;

    @Size(max = 10)
    @JsonProperty("pInAMax2")
    private String pInAMax2;

    @Size(max = 10)
    @JsonProperty("vInMin2")
    private String vInMin2;

    @Size(max = 10)
    @JsonProperty("vInMax2")
    private String vInMax2;

    @Size(max = 10)
    @JsonProperty("iInMin2")
    private String iInMin2;

    @Size(max = 10)
    @JsonProperty("iInMax2")
    private String iInMax2;

    @Size(max = 10)
    @JsonProperty("pInWMin2")
    private String pInWMin2;

    @Size(max = 10)
    @JsonProperty("pInWMax2")
    private String pInWMax2;

    @Size(max = 10)
    @JsonProperty("pFMin2")
    private String pFMin2;

    @Size(max = 10)
    @JsonProperty("pFMax2")
    private String pFMax2;

    @Size(max = 10)
    @JsonProperty("pPrev2")
    private String pPrev2;

    @Size(max = 10)
    @JsonProperty("vInPrev2")
    private String vInPrev2;

    @Size(max = 10)
    @JsonProperty("iInPrev2")
    private String iInPrev2;

    @Size(max = 10)
    @JsonProperty("pInPrev2")
    private String pInPrev2;

    @Size(max = 10)
    @JsonProperty("pFPrev2")
    private String pFPrev2;

}
