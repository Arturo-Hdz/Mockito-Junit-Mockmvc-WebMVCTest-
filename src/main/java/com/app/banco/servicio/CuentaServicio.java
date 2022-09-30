package com.app.banco.servicio;

import java.math.BigDecimal;
import java.util.List;

import com.app.banco.entidades.Cuenta;

public interface CuentaServicio {
	
	public List<Cuenta>findAll();
	public Cuenta findById(Long id);
	public Cuenta save(Cuenta cuenta);
	public int revisarTotalDeTransferencias(Long bancoId);
	public BigDecimal revisarSaldo(Long cuentaId);
	public void transferirDinero(Long numeroCuentaOrigen, Long numeroCuentaDestino, BigDecimal monto, Long bancoId);
	
}
