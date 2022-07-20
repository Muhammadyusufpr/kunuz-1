package com.company.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TagDTO extends BaseDTO {

    private String key;

    @NotBlank(message = "name is won't be Null")
    private String name;

    private String nameUz;
    private String nameRu;
    private String nameEn;

    private Boolean visible;

    private Integer profileId;

}
