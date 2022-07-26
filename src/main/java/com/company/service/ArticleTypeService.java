package com.company.service;

import com.company.dto.ArticleTypeDTO;
import com.company.entity.ArticleTypeEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.LangEnum;
import com.company.enums.ProfileRole;
import com.company.exp.AppBadRequestException;
import com.company.exp.AppForbiddenException;
import com.company.exp.RegionAlreadyExistsException;
import com.company.repository.ArticleTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleTypeService {
    @Autowired
    private ProfileService profileService;
    @Autowired
    private ArticleTypeRepository articleRepository;

    public ArticleTypeDTO create(ArticleTypeDTO dto, Integer pId) {
        ProfileEntity profile = profileService.get(pId);
        if (!profile.getRole().equals(ProfileRole.ADMIN)) {
            throw new AppForbiddenException("Not access");
        }
//        ArticleTypeValidation.isValid(dto);
        ArticleTypeEntity article = articleRepository.findByKey(dto.getKey());
        if (article != null) {
            throw new RegionAlreadyExistsException("Region Alredy Exists");
        }
        ArticleTypeEntity entity = new ArticleTypeEntity();
        entity.setNameUz(dto.getNameUz());
        entity.setNameEn(dto.getNameEn());
        entity.setNameRu(dto.getNameRu());
        entity.setKey(dto.getKey());
        entity.setProfileId(pId);

        articleRepository.save(entity);
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public PageImpl<ArticleTypeDTO> getList(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<ArticleTypeEntity> pagination = articleRepository.findAll(pageable);

        List<ArticleTypeEntity> profileEntityList = pagination.getContent();
        long totalElement = pagination.getTotalElements();
        List<ArticleTypeDTO> dtoList = profileEntityList.stream().map(this::toDTO).toList();
        return new PageImpl<ArticleTypeDTO>(dtoList, pageable, totalElement);
    }

    public ArticleTypeDTO getById(Integer id) {
        Optional<ArticleTypeEntity> optional = articleRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadRequestException("Id Not Found");
        }
        ArticleTypeEntity article = optional.get();
        return toDTO(article);
    }

    public String update(Integer id, ArticleTypeDTO dto) {
        ProfileEntity profile = profileService.get(dto.getProfileId());
        if (!profile.getRole().equals(ProfileRole.ADMIN)) {
            throw new AppForbiddenException("Not access");
        }
        Optional<ArticleTypeEntity> optional = articleRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadRequestException("Id Not Found");
        }
//        ArticleTypeValidation.isValid(dto);
        ArticleTypeEntity entity = articleRepository.findByKey(dto.getNameUz());
        if (entity != null) {
            throw new RegionAlreadyExistsException("Region alredy exists");
        }
        ArticleTypeEntity article = optional.get();
        article.setNameUz(dto.getNameUz());
        article.setNameEn(dto.getNameEn());
        article.setNameRu(dto.getNameRu());
        article.setKey(dto.getKey());
        article.setProfileId(dto.getProfileId());
        articleRepository.save(article);
        return "Success";
    }

    public String delete(Integer id) {

        Optional<ArticleTypeEntity> optional = articleRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadRequestException("Id Not Found");
        }
        ArticleTypeEntity entity = optional.get();
        articleRepository.delete(entity);
        return "Success";
    }

    private ArticleTypeDTO toDTO(ArticleTypeEntity entity) {
        ArticleTypeDTO dto = new ArticleTypeDTO();
        dto.setId(entity.getId());
        dto.setNameUz(entity.getNameUz());
        dto.setNameEn(entity.getNameEn());
        dto.setNameRu(entity.getNameRu());
        dto.setKey(entity.getKey());
        dto.setProfileId(entity.getProfileId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public List<ArticleTypeDTO> getRegionList(LangEnum lang) {
        List<ArticleTypeEntity> entityList = articleRepository.findAll();

        List<ArticleTypeDTO> list = new ArrayList<>();
        for (ArticleTypeEntity entity : entityList) {
            ArticleTypeDTO dto = new ArticleTypeDTO();
            dto.setId(entity.getId());
            dto.setKey(entity.getKey());
            switch (lang) {
                case uz: {
                    dto.setName(entity.getNameUz());
                }
                case en: {
                    dto.setName(entity.getNameEn());
                }
                case ru: {
                    dto.setName(entity.getNameRu());
                }
            }
            list.add(dto);
        }
        return list;
    }
}
