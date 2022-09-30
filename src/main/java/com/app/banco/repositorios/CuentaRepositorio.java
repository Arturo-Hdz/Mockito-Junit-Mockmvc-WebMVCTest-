package com.app.banco.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.banco.entidades.Cuenta;

public interface CuentaRepositorio extends JpaRepository<Cuenta, Long> {

	@Query("select c from Cuenta c where c.persona=?1")
	public Optional<Cuenta> findByPersona(String persona);
	
}
