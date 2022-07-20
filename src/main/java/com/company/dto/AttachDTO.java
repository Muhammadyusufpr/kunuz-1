package com.company.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.JoinColumn;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttachDTO {

    private String id;
    @NotBlank(message = "url required")
    private String url;
    private String origenName;
    private LocalDateTime createdDate;
    private String path;

    public AttachDTO() {

    }

    public AttachDTO(String url) {
        this.url = url;
    }
}
