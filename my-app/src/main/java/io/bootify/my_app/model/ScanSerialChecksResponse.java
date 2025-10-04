package io.bootify.my_app.model;

public interface ScanSerialChecksResponse {

    Long getSerialId();

    String getSerialBoard();

    String getSerialItem();

    String getSerialStatus();

    String getSerialCheck();

    String getTimeScan();

    String getTimeCheck();

    String getResultCheck();

    String getWorkOrder();

    Integer getMachineId();

    Integer getProductionOrderId();
    String getMachineName();
    Integer getStageNum();
}
