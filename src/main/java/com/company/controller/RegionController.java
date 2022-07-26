package com.company.controller;


import com.company.dto.RegionDTO;
import com.company.enums.LangEnum;
import com.company.enums.ProfileRole;
import com.company.exp.TokenNotValidException;
import com.company.service.CategoryService;
import com.company.service.RegionService;
import com.company.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
@Slf4j
@RestController
@RequestMapping("/region")
public class RegionController {
    @Autowired
    private RegionService regionService;

    @PostMapping("/adm")
    public ResponseEntity<?> create(@RequestBody RegionDTO dto,
                                    HttpServletRequest request) {
        Integer pid = JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        log.info("Region {}", dto);
        return ResponseEntity.ok(regionService.create(dto, pid));
    }

    @GetMapping("/adm")
    public ResponseEntity<?> list(HttpServletRequest request) {
        Integer pid = JwtUtil.getIdFromHeader(request);
        return ResponseEntity.ok(regionService.list());
    }

    @GetMapping("/public/{lang}")
    public ResponseEntity<?> list(@PathVariable("lang") LangEnum lang) {
        return ResponseEntity.ok(regionService.list(lang));
    }

    @PutMapping("/adm/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody RegionDTO dto,
                                    HttpServletRequest request) {
        Integer pid = JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(regionService.update(id, dto));
    }

    @DeleteMapping("/adm/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id, HttpServletRequest request) {
        Integer pid = JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(regionService.delete(id));
    }
}
