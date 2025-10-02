package io.bootify.my_app.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UsersModelsDTO {

    private Integer userId;

    @Size(max = 20)
    private String userName;

    @Size(max = 20)
    private String passwords;

    @Size(max = 20)
    private String phoneNumber;

    @Size(max = 100)
    private String descriptions;

}
