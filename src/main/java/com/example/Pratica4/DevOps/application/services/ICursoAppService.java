package com.example.Pratica4.DevOps.application.services;

import com.example.Pratica4.DevOps.application.dtos.CursoDTO;

import java.util.List;
import java.util.Optional;

public interface ICursoAppService {
    CursoDTO create(CursoDTO dto);
    Optional<CursoDTO> findById(String codigo);
    List<CursoDTO> findAll();
}
