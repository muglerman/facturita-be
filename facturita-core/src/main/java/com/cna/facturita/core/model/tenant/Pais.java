package com.cna.facturita.core.model.tenant;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "t_paises")
@Table(name = "t_paises")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pais {
    @Id
    @Column(length = 2)
    private String id;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Builder.Default
    @Column(nullable = false)
    private boolean estado = true;

}
