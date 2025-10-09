package io.bootify.my_app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SerialCheckResponse {
    private Integer code;
    private String message;
}
