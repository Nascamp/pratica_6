package com.example.Pratica4.DevOps.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CursoDTO {
    private String codigo;
    private String titulo;
    private String modalidade;
    private int progressoPercentual;
}