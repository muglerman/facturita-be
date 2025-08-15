package com.cna.facturita.dto.form;

import com.cna.facturita.core.model.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


/**
 * Formulario DTO para la creación y edición de usuarios.
 * <p>
 * Utilizado para recibir datos desde la capa de presentación y aplicar validaciones antes de persistir o actualizar entidades {@link Usuario}.
 * Incluye validaciones de campos y lógica para reglas de negocio específicas.
 * <p>
 * <b>Ejemplo de uso:</b>
 * <pre>
 * UsuarioForm form = ...;
 * Usuario usuario = new Usuario();
 * form.applyTo(usuario);
 * </pre>
 *
 * @author Equipo Facturita
 * @since 2025
 */
@Data
public class UsuarioForm {
    
    /** Identificador único del usuario (opcional para edición). */
    private Integer id;
    
    /** Nombre completo del usuario. No puede estar vacío y debe tener entre 2 y 100 caracteres. */
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;
    
    /** Correo electrónico del usuario. No puede estar vacío y debe tener formato válido. */
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe tener un formato válido")
    private String email;
    
    /** Contraseña del usuario. No puede estar vacía y debe tener al menos 6 caracteres. */
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;
    
    /**
     * Aplica los datos del formulario a una entidad {@link Usuario}.
     * <p>
     * Útil para mapear los datos validados del DTO a la entidad antes de persistir o actualizar en la base de datos.
     * <b>Nota:</b> La contraseña debe ser encriptada antes de asignar en producción.
     *
     * @param usuario Entidad de usuario a modificar
     */
    public void applyTo(Usuario usuario) {
        usuario.setNombre(this.nombre);
        usuario.setEmail(this.email);
        // La contraseña se debería encriptar antes de asignar
        usuario.setPassword(this.password);
    }
}
