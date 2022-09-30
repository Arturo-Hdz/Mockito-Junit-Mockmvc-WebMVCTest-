package com.app.banco.servicio;

import java.math.BigDecimal;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.banco.entidades.Banco;
import com.app.banco.entidades.Cuenta;
import com.app.banco.repositorios.BancoRepositorio;
import com.app.banco.repositorios.CuentaRepositorio;

@Service
public class CuentaServicoImpl implements CuentaServicio{
	
	@Autowired
	private CuentaRepositorio cuentaRepositorio;
	
	@Autowired
	private BancoRepositorio bancoRepositorio;

	public CuentaServicoImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	@Transactional(readOnly = true)
	public List<Cuenta> findAll() {
		// TODO Auto-generated method stub
		return cuentaRepositorio.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Cuenta findById(Long id) {
		// TODO Auto-generated method stub
		return cuentaRepositorio.findById(id).orElseThrow();
	}

	@Override
	@Transactional
	public Cuenta save(Cuenta cuenta) {
		// TODO Auto-generated method stub
		return cuentaRepositorio.save(cuenta);
	}

	@Override
	@Transactional(readOnly = true)
	public int revisarTotalDeTransferencias(Long bancoId) {
		// TODO Auto-generated method stub
		Banco banco = bancoRepositorio.findById(bancoId).orElseThrow();
		return banco.getTotalTransferencias();
	}

	@Override
	@Transactional(readOnly = true)
	public BigDecimal revisarSaldo(Long cuentaId) {
		// TODO Auto-generated method stub
		Cuenta cuenta = cuentaRepositorio.findById(cuentaId).orElseThrow();
		return cuenta.getSaldo();
	}

	@Override
	public void transferirDinero(Long numeroCuentaOrigen, Long numeroCuentaDestino, BigDecimal monto, Long bancoId) {
		// TODO Auto-generated method stub
		Cuenta cuentaOrigen = cuentaRepositorio.findById(numeroCuentaOrigen).orElseThrow();
		cuentaOrigen.realizarDebito(monto);
		cuentaRepositorio.save(cuentaOrigen);
		
		Cuenta cuentaDestino = cuentaRepositorio.findById(numeroCuentaDestino).orElseThrow();
		cuentaDestino.realizarCredito(monto);
		cuentaRepositorio.save(cuentaDestino);
		
		Banco banco = bancoRepositorio.findById(bancoId).orElseThrow();
		int totalTransferencias = banco.getTotalTransferencias();
		banco.setTotalTransferencias(++totalTransferencias);
		bancoRepositorio.save(banco);
	}

}
