package io.bootify.my_app.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="error_common_scada")
@Setter
@Getter
public class ErrorCommonScada {
    @Id
    @Column
    private Long id;

    @Column(name = "json_name")
    private String jsonName;
    @Column(name = "name")
    private String name;
    @Column(name = "err_group")
    private String errGroup;
    @Column(name = "err_name")
    private String errName;
    @Column(name="err_type")
    private String errType;
}
