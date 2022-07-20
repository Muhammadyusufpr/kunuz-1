package com.company.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDTO {
    private Integer id;
    @NotBlank(message = "key required")
    private String key;
    private String name;
    private String nameUz;
    private String nameRu;
    private String nameEn;
    private Integer profileId;
    private LocalDateTime createdDate;

}
