package com.company.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleTypeDTO {
    private Integer id;
    @NotBlank(message = "key is won't be Null")
    private String key;
    @NotBlank(message = "name is won't be Null")
    private String name;
    private Integer profileId;
    private String nameUz;
    private String nameRu;
    private String nameEn;
    private LocalDateTime createdDate;
}
