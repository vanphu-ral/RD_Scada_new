package io.bootify.my_app.domain;

import jakarta.persistence.*;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "ScanCheckSerialLogs")
public class ScanCheckSerialLogs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "serial_check")
    private String serialCheck;
    @Column(name = "result")
    private String result;
    @Column(name = "time_check")
    private String timeCheck;
    @Column(name="wo")
    private String wo;
    @Column(name = "user_name")
    private String userName;
}
