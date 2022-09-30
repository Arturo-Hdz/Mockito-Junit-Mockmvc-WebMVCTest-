package com.app.banco.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.banco.entidades.Banco;

public interface BancoRepositorio extends JpaRepository<Banco, Long> {

}
