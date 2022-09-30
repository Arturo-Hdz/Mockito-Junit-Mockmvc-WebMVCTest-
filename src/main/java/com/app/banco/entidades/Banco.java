package com.app.banco.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "bancos")
public class Banco {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
		
		private String nombre;
		
		@Column(name = "total_transferencias ")
		private int totalTransferencias;
			
	
	public Banco() {
		// TODO Auto-generated constructor stub
	}


	public Banco(Long id, String nombre, int totalTransferencias) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.totalTransferencias = totalTransferencias;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public int getTotalTransferencias() {
		return totalTransferencias;
	}


	public void setTotalTransferencias(int totalTransferencias) {
		this.totalTransferencias = totalTransferencias;
	}
	
	

}
