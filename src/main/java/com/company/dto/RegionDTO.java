package com.company.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegionDTO {
    private Integer profileId;
    private String nameUz;
    private String nameRu;
    private String nameEn;

    @NotBlank(message = "name is won't be Null")
    private String name;

    private Integer id;
    private String key;
    private LocalDateTime createdDate;
}
