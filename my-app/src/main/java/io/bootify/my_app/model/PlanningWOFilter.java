package io.bootify.my_app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Setter
@Getter
public class PlanningWOFilter {
    private Long id;
    private String bomVersion;
    private String branchCode;
    private String branchName;
    private LocalDateTime createTime;
    private LocalDateTime startTime;
    private LocalDateTime lastUpdateTime;
    private LocalDateTime endTime;
    private String groupCode;
    private String groupName;
    private String lineId;
    private String productCode;
    private String productName;
    private String productOrderId;
    private BigDecimal quantityPlan;
    private String sapWoId;
    private String state;
    private String status;
    private BigDecimal quantityActual;
    private String planningWorkOrderId;
    private String woId;
    private String lotNumber;
    private String workOrderType;
    private String workOrderTypeName;
    private String parentWorkOrderId;
    private Integer numberStaff;
    private String note;
    private Boolean isNew;
    private BigDecimal quota;
    private String profileId;
    private String scadaUserName;
    private String scadaUserGroup;
    private String scadaUserFullname;
    private String scadaStageList;
}
