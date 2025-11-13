package io.bootify.my_app.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Table(name = "ScanSerialCheckLogs")
public class ScanSerialCheckLogs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, name = "Id")
    private Long id;

    @Column(length = 255, name = "serial_check")
    private String serialCheck;

    @Column(length = 255, name = "result")
    private String result;

    @Column(columnDefinition = "datetime2", name = "time_check")
    private LocalDateTime timeCheck;

    @Column(length = 255, name = "wo")
    private String workOrder;
}
