package com.example.Pratica4.DevOps.infrastructure.mappers;

import com.example.Pratica4.DevOps.domain.entities.Curso;
import com.example.Pratica4.DevOps.infrastructure.entities.CursoEntity;

public class CursoMapper {

    public static CursoEntity toEntity(Curso domain) {
        if (domain == null) return null;
        return new CursoEntity(domain.getCodigo(), domain.getTitulo(), domain.getModalidade().name(), domain.getProgressoPercentual());
    }

    public static Curso toDomain(CursoEntity entity) {
        if (entity == null) return null;
        com.example.Pratica4.DevOps.domain.valueobjects.Modalidade modalidade = com.example.Pratica4.DevOps.domain.valueobjects.Modalidade.valueOf(entity.getModalidade());
        return new Curso(entity.getCodigo(), entity.getTitulo(), modalidade, entity.getProgressoPercentual());
    }
}