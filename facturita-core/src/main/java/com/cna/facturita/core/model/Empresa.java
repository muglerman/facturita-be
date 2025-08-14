package com.cna.facturita.core.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Entity
@Data
@Table(name = "empresas")
@NoArgsConstructor
@AllArgsConstructor
public class Empresa {
    private static final Logger log = LoggerFactory.getLogger(Empresa.class);
	@Id
    private String ruc;
    @Column(name = "razon_social")
    private String razonSocial;

    @PrePersist
    protected void onCreate() {
        log.info("Creando nueva empresa con RUC: '{}' y razón social: '{}'", this.ruc, this.razonSocial);
    }

    @PreUpdate
    protected void onUpdate() {
        log.info("Actualizando empresa RUC: '{}', razón social: '{}'", this.ruc, this.razonSocial);
    }

    @PreRemove
    protected void onDelete() {
        log.info("Eliminando empresa RUC: '{}', razón social: '{}'", this.ruc, this.razonSocial);
    }
}
