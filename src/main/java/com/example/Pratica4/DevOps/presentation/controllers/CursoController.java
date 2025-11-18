package com.example.Pratica4.DevOps.presentation.controllers;

import com.example.Pratica4.DevOps.application.dtos.CursoDTO;
import com.example.Pratica4.DevOps.application.services.ICursoAppService;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    private final ICursoAppService service;

    public CursoController(ICursoAppService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CursoDTO>> getAll() {
        List<CursoDTO> all = service.findAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoDTO> getById(@PathVariable("id") String id) {
        return service.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CursoDTO> create(@RequestBody CursoDTO dto) {
        CursoDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}