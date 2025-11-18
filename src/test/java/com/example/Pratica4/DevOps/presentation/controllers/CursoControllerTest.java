package com.example.Pratica4.DevOps.presentation.controllers;

import com.example.Pratica4.DevOps.application.dtos.CursoDTO;
import com.example.Pratica4.DevOps.application.services.ICursoAppService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.Matchers.containsString;

@WebMvcTest(controllers = CursoController.class)
public class CursoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ICursoAppService service;

    @Test
    void getAllShouldReturnOk() throws Exception {
        when(service.findAll()).thenReturn(List.of(new CursoDTO("C1","T","ONLINE",0)));
        mvc.perform(get("/api/cursos"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("C1")));
    }

    @Test
    void getByIdShouldReturnOk() throws Exception {
        when(service.findById("C1")).thenReturn(Optional.of(new CursoDTO("C1","T","ONLINE",0)));
        mvc.perform(get("/api/cursos/C1"))
                .andExpect(status().isOk());
    }
}