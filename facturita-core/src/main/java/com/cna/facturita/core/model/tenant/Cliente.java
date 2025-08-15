package com.cna.facturita.core.model.tenant;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_documento_identidad_id", referencedColumnName = "id", nullable = false)
    private TipoDocumentoIdentidad tipoDocumentoIdentidad;

    @Column(nullable = false, length = 255)
    private String numero;

    @Column(nullable = false, length = 255)
    private String nombre;

    @Column(name = "nombre_comercial", length = 255)
    private String nombreComercial;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pais_id", referencedColumnName = "id", nullable = false)
    private Pais pais;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "distrito_id", referencedColumnName = "id")
    private Distrito distrito;

    @Column(length = 255)
    private String direccion;

    @Column(nullable = false)
    private boolean estado;

    @Column(unique = true, length = 255)
    private String email;

    @Column(length = 255)
    private String telefono;

    @Column(columnDefinition = "TEXT")
    private String observacion;

    @Column(name = "fecha_creacion", columnDefinition = "TIMESTAMP")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion", columnDefinition = "TIMESTAMP")
    private LocalDateTime fechaActualizacion;

}
