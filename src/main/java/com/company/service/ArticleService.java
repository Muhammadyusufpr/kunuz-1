package com.company.service;

import com.company.dto.ArticleDTO;
import com.company.entity.ArticleEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.ArticleStatus;
import com.company.enums.LangEnum;
import com.company.exp.ItemAlreadyExistsException;
import com.company.exp.ItemNotFoundException;
import com.company.mapper.ArticleSimpleMapper;
import com.company.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private AttachService attachService;

    public ArticleDTO create(ArticleDTO dto, Integer pId) {
//        ArticleValidation.isValid(dto); // validation

        Optional<ArticleEntity> optional = articleRepository.findByTitle(dto.getTitle());
        if (optional.isPresent()) {
            throw new ItemAlreadyExistsException("This Article already used!");
        }

        ArticleEntity entity = new ArticleEntity();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
        entity.setProfileId(pId);

        entity.setStatus(ArticleStatus.CREATED);

        entity.setAttachId(dto.getAttachId());
        entity.setCategoryId(dto.getCategoryId());
        entity.setRegionId(dto.getRegionId());
        entity.setTypeId(dto.getTypeId());
        entity.setTagList(dto.getTagIdList());

        articleRepository.save(entity);
        return toDTO(entity);
    }

    public List<ArticleDTO> list(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));

        List<ArticleDTO> dtoList = new ArrayList<>();

        articleRepository.findByVisible(true, pageable).forEach(entity -> {
            dtoList.add(toDTO(entity));
        });

        return dtoList;
    }

    public ArticleDTO update(Integer id, ArticleDTO dto, Integer pId) {
        ProfileEntity profileEntity = profileService.get(pId);

//        ArticleValidation.isValid(dto); // validation

        ArticleEntity entity = articleRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Not Found!"));

        if (!entity.getVisible()) {
            throw new ItemNotFoundException("Not Found!");
        }

        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
        entity.setProfile(profileEntity);
        entity.setUpdatedDate(LocalDateTime.now());

        articleRepository.save(entity);
        return toDTO(entity);
    }

    public Boolean delete(Integer id) {
        ArticleEntity entity = articleRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Not Found!"));

        if (!entity.getVisible()) {
            throw new ItemNotFoundException("Not Found!");
        }

        int n = articleRepository.updateVisible(false, id);
        return n > 0;
    }

    public List<ArticleDTO> getArticleByType(Integer typeId) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
//        List<ArticleEntity> entityList = articleRepository.findTop5ByTypeIdAndStatus(typeId, ArticleStatus.PUBLISHED, sort);
        List<ArticleSimpleMapper> entityList = articleRepository.getTypeId(typeId, ArticleStatus.PUBLISHED.name());

        /*Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "createdDate"));
        Page<ArticleEntity> page = articleRepository.findAllByTypeId(typeId, pageable);*/
        List<ArticleDTO> dtoList = new LinkedList<>();
        entityList.forEach(entity -> {
            ArticleDTO dto = new ArticleDTO();
            dto.setId(entity.getId());
            dto.setTitle(entity.getTitle());
            dto.setDescription(entity.getDescription());
            dto.setPublishedDate(entity.getPublished_date());

            dto.setImage(attachService.toOpenURLDTO(entity.getAttach_id()));
            dtoList.add(dto);
        });
        return dtoList;
    }

    public List<ArticleDTO> getByIdPublished(Integer articleId, LangEnum lang) {
        articleRepository.findByIdandStatus(articleId, ArticleStatus.PUBLISHED);
        return null;
    }

    public ArticleDTO getByIdAdAdmin(){
        return null;
    }
//    public PageImpl<ArticleDTO> getByProfileId(Integer profileId) {
//
//    }

    private ArticleDTO toSimpleDTO(ArticleEntity entity) {
        ArticleDTO dto = new ArticleDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setPublishedDate(entity.getPublishedDate());

        dto.setImage(attachService.toOpenURLDTO(entity.getAttachId()));

        return dto;
    }

    private ArticleDTO toDTO(ArticleEntity entity) {
        ArticleDTO dto = new ArticleDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setContent(entity.getContent());
        dto.setProfileId(entity.getProfileId());
        dto.setUpdatedDate(entity.getUpdatedDate());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setPublishedDate(entity.getPublishedDate());
        //
        return dto;
    }

    public ArticleEntity get(Integer articleId) {
        return articleRepository.findById(articleId).orElseThrow(() -> {
            throw new ItemNotFoundException("Article Not found");
        });
    }
}
