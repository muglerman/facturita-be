package com.cna.facturita.core.model.tenant;

import java.util.List;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "t_provincias")
@Table(name = "t_provincias")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Provincia {
    @Id
    @Column(length = 4)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departamento_id", referencedColumnName = "id", nullable = false)
    private Departamento departamento;

    @OneToMany(mappedBy = "provincia", fetch = FetchType.LAZY)
    private List<Distrito> distritos;

    @Column(nullable = false, length = 255)
    private String nombre;

    @Builder.Default
    @Column(nullable = false)
    private boolean estado = true;

}
