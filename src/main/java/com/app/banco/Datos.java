package com.app.banco;

import java.math.BigDecimal;
import java.util.Optional;

import com.app.banco.entidades.Banco;
import com.app.banco.entidades.Cuenta;

public class Datos {

	public static Optional<Cuenta> crearCuenta001(){
		return Optional.of(new Cuenta (1L, "Cristian", new BigDecimal("1000")));
	}
	
	public static Optional<Cuenta> crearCuenta002(){
		return Optional.of(new Cuenta (2L, "Julio", new BigDecimal("2000")));
	}
	
	public static Optional<Banco> crearBanco(){
		return Optional.of(new Banco (1L, "El Banco Financiero", 0));
	}
	
	public Datos() {
		// TODO Auto-generated constructor stub
	}

}
