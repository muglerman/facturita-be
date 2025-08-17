package com.cna.facturita.core.model.tenant;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "t_distritos")
@Table(name = "t_distritos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Distrito {
    @Id
    @Column(length = 6)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provincia_id", referencedColumnName = "id", nullable = false)
    private Provincia provincia;


    @Column(nullable = false, length = 255)
    private String nombre;

    @Builder.Default
    @Column(nullable = false)
    private boolean estado = true;

}
