package com.example.Pratica4.DevOps.application.services;

import com.example.Pratica4.DevOps.application.dtos.CursoDTO;
import com.example.Pratica4.DevOps.infrastructure.entities.CursoEntity;
import com.example.Pratica4.DevOps.infrastructure.repositories.CursoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CursoAppService implements ICursoAppService {

    private final CursoRepository repo;

    public CursoAppService(CursoRepository repo) {
        this.repo = repo;
    }

    @Override
    public CursoDTO create(CursoDTO dto) {
        CursoEntity e = new CursoEntity(dto.getCodigo(), dto.getTitulo(), dto.getModalidade(), dto.getProgressoPercentual());
        CursoEntity saved = repo.save(e);
        return new CursoDTO(saved.getCodigo(), saved.getTitulo(), saved.getModalidade(), saved.getProgressoPercentual());
    }

    @Override
    public Optional<CursoDTO> findById(String codigo) {
        return repo.findById(codigo).map(e -> new CursoDTO(e.getCodigo(), e.getTitulo(), e.getModalidade(), e.getProgressoPercentual()));
    }

    @Override
    public List<CursoDTO> findAll() {
        return repo.findAll().stream().map(e -> new CursoDTO(e.getCodigo(), e.getTitulo(), e.getModalidade(), e.getProgressoPercentual())).collect(Collectors.toList());
    }
}