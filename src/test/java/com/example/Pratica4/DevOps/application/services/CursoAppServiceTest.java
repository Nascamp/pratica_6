package com.example.Pratica4.DevOps.application.services;

import com.example.Pratica4.DevOps.infrastructure.repositories.CursoRepository;
import com.example.Pratica4.DevOps.infrastructure.entities.CursoEntity;
import com.example.Pratica4.DevOps.application.dtos.CursoDTO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

public class CursoAppServiceTest {

    @Mock
    private CursoRepository repo;

    @InjectMocks
    private CursoAppService service;

    public CursoAppServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createShouldSave() {
        CursoDTO dto = new CursoDTO("C200","Teste","PRESENCIAL",0);
        when(repo.save(any(CursoEntity.class))).thenAnswer(i -> i.getArgument(0));
        CursoDTO out = service.create(dto);
        assertThat(out.getCodigo()).isEqualTo("C200");
        verify(repo, times(1)).save(any(CursoEntity.class));
    }

    @Test
    void findByIdShouldReturn() {
        CursoEntity e = new CursoEntity("C1","X","ONLINE", 5);
        when(repo.findById("C1")).thenReturn(Optional.of(e));
        Optional<CursoDTO> result = service.findById("C1");
        assertThat(result).isPresent();
        assertThat(result.get().getTitulo()).isEqualTo("X");
    }
}
