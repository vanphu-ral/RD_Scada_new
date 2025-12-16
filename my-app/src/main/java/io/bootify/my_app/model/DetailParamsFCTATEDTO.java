package io.bootify.my_app.model;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DetailParamsFCTATEDTO {
    // 1. Long
    private Long paramsID;
    // 2. Long
    private Long serialID;
    // 3. String
    private String programName;
    // 4. String
    private String machineName;
    // 5. String
    private String serial;      // (CASE WHEN s.serialBoard...)
    // 6. String
    private String results;     // <--- ĐƯA LÊN VỊ TRÍ 6 (Sau serial)
    // 7. String
    private String fixLR;
    // 8. Integer
    private Integer fixID;
    // 9. LocalDateTime
    private LocalDateTime startTestTime;
    // 10. LocalDateTime
    private LocalDateTime endTestTime;
    // 11. String
    private String timeElapsed;
    // 12. String
    private String detailParams; // <--- ĐƯA XUỐNG VỊ TRÍ 12 (Cuối cùng)
}
