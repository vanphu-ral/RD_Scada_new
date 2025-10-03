package io.bootify.my_app.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "planningwo")
public class PlanningWO {
    @Id
    private Long id;
    @Column(name = "bom_version")
    private String bomVersion;
    @Column(name = "branch_code")
    private String branchCode;
    @Column(name = "branch_name")
    private String branchName;
    @Column(name = "create_time")
    private LocalDateTime createTime;
    @Column(name = "START_TIME")
    private LocalDateTime startTime;
    @Column(name = "last_update_time")
    private LocalDateTime lastUpdateTime;
    @Column(name="END_TIME")
    private LocalDateTime endTime;
    @Column(name = "group_code")
    private String groupCode;
    @Column(name = "group_name")
    private String groupName;
    @Column(name = "LINE_ID")
    private String lineId;
    @Column(name="product_code")
    private String productCode;
    @Column(name="PRODUCT_NAME")
    private String productName;
    @Column(name="PRODUCT_ORDER_ID")
    private String productOrderId;
    @Column(name="QUANTITY_PLAN")
    private BigDecimal quantityPlan;
    @Column(name="sap_WO_ID")
    private String sapWoId;
    @Column(name="state")
    private String state;
    @Column(name="status")
    private String status;
    @Column(name="QUANTITY_ACTUAL")
    private BigDecimal quantityActual;
    @Column(name="planning_work_order_id")
    private String planningWorkOrderId;
    @Column(name="wo_id")
    private String woId;
    @Column(name="lot_number")
    private String lotNumber;
    @Column(name="work_order_type")
    private String workOrderType;
    @Column(name="work_order_type_name")
    private String workOrderTypeName;
    @Column(name="parent_work_order_id")
    private String parentWorkOrderId;
    @Column(name="number_staff")
    private Integer numberStaff;
    @Column(name="note")
    private String note;
    @Column(name="is_new")
    private Boolean isNew;
    @Column(name="quota")
    private BigDecimal quota;
    @Column(name="profile_id")
    private String profileId;
    @Column(name="scada_user_name")
    private String scadaUserName;
    @Column(name="scada_user_group")
    private String scadaUserGroup;
    @Column(name="scada_user_fullname")
    private String scadaUserFullname;
    @Column(name="scada_stage_list")
    private String scadaStageList;
    @Column(name="scada_quantity_out")
    private BigDecimal scadaQuantityOut;
    @Column(name="scada_asset_id")
    private String scadaAssetId;
    @Column(name="num_of_return_material")
    private Integer numOfReturnMaterial;
    @Column(name="status_return_material")
    private String statusReturnMaterial;
    @Column(name="reason_id")
    private String reasonId;
    @Column(name="scada_quantity_out1")
    private BigDecimal scadaQuantityOut1;
    @Column(name="process_status")
    private String processStatus;
    @Column(name="latest_time_create_dnlnvl")
    private LocalDateTime latestTimeCreateDnlnvl;
    @Column(name="classify")
    private String classify;
    @Column(name="priority")
    private String priority;
    @Column(name="product_type")
    private String productType;
    @Column(name="phone_number")
    private String phoneNumber;
    @Column(name="recurrence_rule")
    private String recurrenceRule;
}
