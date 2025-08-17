package com.cna.facturita.core.model.tenant;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity(name = "t_usuario")
@Table(name = "t_usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioTenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombre;
    
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String usuario;
    
    @Column(name = "fecha_verificacion_email")
    private LocalDateTime fechaVerificacionEmail;

    @Column(nullable = false)
    private String password;

    @Column(name = "establecimiento_id")
    private Integer establecimientoId;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_usuario", nullable = false)
    private TipoUsuario tipoUsuario;

    @Column(nullable = false)
    private boolean estado = true;

    /* @Column(nullable = false)
    private Boolean isMultiUser = false;

    private Integer multiUserId;

    @Column(nullable = false)
    private Boolean permissionForceSendBySummary = false;

    @Column(nullable = false)
    private Boolean permissionEditCpe = false;

    @Column(nullable = false)
    private Boolean permissionEditItemPrices = true;

    @Column(nullable = false)
    private Boolean createPayment = true;

    @Column(nullable = false)
    private Boolean deletePurchase = true;

    @Column(nullable = false)
    private Boolean annularPurchase = true;

    @Column(nullable = false)
    private Boolean editPurchase = true;

    @Column(nullable = false)
    private Boolean deletePayment = true;

    @Column(nullable = false)
    private Boolean recreateDocuments = false;
 */
    @Column(nullable = false)
    private Boolean bloqueado = false;
/* 
    @Column(nullable = false)
    private Boolean multipleDefaultDocumentTypes = false;

    private String photoFilename;
    private String position;
    private LocalDate contractDate;
    private LocalDate dateOfBirth;
    private String corporateCellPhone;
    private String personalCellPhone;
    private String corporateEmail;
    private String personalEmail;
    private String lastNames;
    private String names;
    private String identityDocumentTypeId;
    private String number;
    private String address;
    private String telephone;
    private String rememberToken;
    private LocalDateTime lastPasswordUpdate;
    private Integer restaurantRoleId;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String documentId;
    private Integer seriesId;
    private Integer zoneId;
 */

    public enum TipoUsuario {
        ADMIN, WORKER, CLIENT
    }
}
