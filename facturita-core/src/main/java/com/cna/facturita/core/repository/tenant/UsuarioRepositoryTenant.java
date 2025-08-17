package com.cna.facturita.core.repository.tenant;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cna.facturita.core.model.tenant.UsuarioTenant;

@Repository("UsuarioRepositoryTenant")
public interface UsuarioRepositoryTenant extends JpaRepository<UsuarioTenant, Integer> {

    Optional<UsuarioTenant> findByEmail(String email);
}