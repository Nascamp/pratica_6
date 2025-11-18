package com.example.Pratica4.DevOps.infrastructure.repositories;

import com.example.Pratica4.DevOps.infrastructure.entities.CursoEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CursoRepositoryTest {

    @Autowired
    private CursoRepository repo;

    @Test
    void saveAndFind() {
        CursoEntity e = new CursoEntity("C101", "Curso Teste", "ONLINE", 10);
        repo.save(e);

        List<CursoEntity> all = repo.findAll();
        assertThat(all).isNotEmpty();
        assertThat(all.get(0).getCodigo()).isEqualTo("C101");
    }
}
