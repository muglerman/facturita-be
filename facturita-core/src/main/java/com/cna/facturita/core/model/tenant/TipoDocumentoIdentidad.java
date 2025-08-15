package com.cna.facturita.core.model.tenant;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tipos_documento_identidad")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoDocumentoIdentidad {
    @Id
    @Column(length = 2)
    private String id;

    @Column(nullable = false, length = 255)
    private String nombre;

    @Column(nullable = false, length = 20)
    private String abreviatura;

    @Builder.Default
    @Column(nullable = false)
    private boolean estado = true;

}
