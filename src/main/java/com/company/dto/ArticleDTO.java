package com.company.dto;

import com.company.entity.ProfileEntity;
import com.company.enums.ArticleStatus;
import com.company.service.ArticleService;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleDTO {
    private Integer id;
    @NotBlank(message = "title is won't be Null")
    private String title;
    private String description;
    @NotBlank(message = "content is won't be Null")
    private String content;
    private Boolean visible;

    private Integer profileId;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    private ArticleStatus status;
    private LocalDateTime publishedDate;

    private String attachId;
    private Integer categoryId;
    private Integer regionId;
    private Integer typeId;

    private Integer viewCount;
    private Integer sharedCount;

    private List<Integer> tagIdList;

    private AttachDTO image;
    private LikeDTO like;
    private CategoryDTO category;
    private ArticleDTO article;
    private ArticleTypeDTO articleType;
}
