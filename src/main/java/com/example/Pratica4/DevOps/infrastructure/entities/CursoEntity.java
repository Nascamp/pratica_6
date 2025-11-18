package com.example.Pratica4.DevOps.infrastructure.entities;

import jakarta.persistence.*;
import lombok.*;

/**
 * JPA Entity mapping for Curso (infrastructure layer).
 */
@Entity
@Table(name = "cursos")
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CursoEntity {

    @Id
    @Column(nullable = false, unique = true)
    @EqualsAndHashCode.Include
    private String codigo;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String modalidade;

    @Column(name = "progresso_percentual", nullable = false)
    private int progressoPercentual;

    // Lombok generates constructors, getters/setters, toString, equals and hashCode
}