package com.cna.facturita.core.model.tenant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "t_departamentos")
@Table(name = "t_departamentos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Departamento {

    @Id
    @Column(length = 2, nullable = false)
    private String id;

    @Column(nullable = false, length = 255)
    private String nombre;

    @Builder.Default
    @Column(nullable = false)
    private boolean estado = true;

}
