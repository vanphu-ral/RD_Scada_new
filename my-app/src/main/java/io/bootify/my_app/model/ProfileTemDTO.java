package io.bootify.my_app.model;

import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProfileTemDTO {

    private Integer profileId;

    @Size(max = 255)
    private String profileName;

    @Size(max = 255)
    private String imageUrl;

    @Size(max = 255)
    private String rectDetech;

    @Size(max = 1000)
    private String rectCheck;

    @Size(max = 255)
    private String ratio;

    @Size(max = 255)
    private String sizeTem;

    private OffsetDateTime createdDate;

    private Integer product;

}
