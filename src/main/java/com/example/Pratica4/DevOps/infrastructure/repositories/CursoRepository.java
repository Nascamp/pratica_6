package com.example.Pratica4.DevOps.infrastructure.repositories;

import com.example.Pratica4.DevOps.infrastructure.entities.CursoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<CursoEntity, String> {
    // Podemos adicionar queries customizadas aqui se precisar
}