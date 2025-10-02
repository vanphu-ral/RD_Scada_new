package io.bootify.my_app.model;

import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CheckTemDTO {

    private Long checkId;

    @Size(max = 255)
    private String poName;

    @Size(max = 255)
    private String passQlcl;

    @Size(max = 255)
    private String ngQlcl;

    @Size(max = 255)
    private String userQlcl;

    @Size(max = 255)
    private String passBranch;

    @Size(max = 255)
    private String ngBranch;

    @Size(max = 255)
    private String userBranch;

    private OffsetDateTime checkedDate;

    @Size(max = 50)
    private String statusQlcl;

    @Size(max = 50)
    private String statusBranch;

    @Size(max = 50)
    private String totals;

    private Integer profile;

}
