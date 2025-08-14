package com.cna.facturita.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@SpringBootApplication(scanBasePackages = {
//	    "com.cna.facturita.security",
//	    "com.cna.facturita.core"
//	})
@EnableJpaRepositories(basePackages = "com.cna.facturita.core.repository")
@EntityScan(basePackages = "com.cna.facturita.core.model")
public class FacturitaSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(FacturitaSecurityApplication.class, args);
	}

}
