package io.bootify.my_app.model;

public interface ErrorResponse {
    Integer getErrorId();
    String getProductTypeGroupID();
    String getErrorName();
    Integer getQuantity();
    Integer getStageID();
    Integer getHmiErrID();
}
