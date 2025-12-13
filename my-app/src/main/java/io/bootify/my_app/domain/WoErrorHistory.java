package io.bootify.my_app.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Setter
@Getter
@Table(name="woErrorHistory")
public class WoErrorHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "workOrder")
    private String workOrder;
    @Column(name = "sender")
    private String sender;
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;
    @Column(name = "type")
    private String type;
    @Column(name = "status")
    private Integer status;
    @Column(name = "time")
    private LocalDateTime time;
}
