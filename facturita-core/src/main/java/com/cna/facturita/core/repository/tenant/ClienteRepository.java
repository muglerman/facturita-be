package com.cna.facturita.core.repository.tenant;

import com.cna.facturita.core.model.tenant.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}
