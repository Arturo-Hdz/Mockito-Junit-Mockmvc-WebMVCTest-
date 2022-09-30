package com.app.banco;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.app.banco.controlador.CuentaControlador;
import com.app.banco.entidades.Cuenta;
import com.app.banco.entidades.TransaccionDTO;
import com.app.banco.servicio.CuentaServicio;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@WebMvcTest(CuentaControlador.class)
public class CuentaControladorTest {

	@Autowired
	private MockMvc mockMVC;
	
	@MockBean
	private CuentaServicio cuentaServicio;
	
	ObjectMapper objectMapper;
	
	@BeforeEach
	void configurar() {
		objectMapper = new ObjectMapper();
	}
	
	@Test
	void testVerDetalles() throws Exception{ 
		when(cuentaServicio.findById(1L)).thenReturn(Datos.crearCuenta001().orElseThrow());
		
		mockMVC.perform(get("/api/cuentas/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.persona").value("Cristian"))
				.andExpect(jsonPath("$.saldo").value("1000"));
		
		verify(cuentaServicio).findById(1L);
	}
	
	@Test 
	void testTransferirDinero() throws Exception{
		TransaccionDTO transaccionDTO = new TransaccionDTO();
		transaccionDTO.setCuentaOrigenId(1L);
		transaccionDTO.setCuentaDestinoId(2L);
		transaccionDTO.setMonto(new BigDecimal("100"));
		transaccionDTO.setBancoId(1L);
		System.out.println(objectMapper.writeValueAsString(transaccionDTO));
		
		Map<String, Object> respuesta = new HashMap<>();
		respuesta.put("date", LocalDate.now().toString());
		respuesta.put("status", "OK");
		respuesta.put("mensaje", "Transferencia realizada con exito");
		respuesta.put("transaccionDTO", transaccionDTO);
		System.out.println(objectMapper.writeValueAsString(respuesta));
		
		mockMVC.perform(post("/api/cuentas/transferir").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(transaccionDTO)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
				.andExpect(jsonPath("$.mensaje").value("Transferencia realizada con exito"))
				.andExpect(jsonPath("$.transaccionDTO.cuentaOrigenId").value(transaccionDTO.getCuentaOrigenId()))
				.andExpect(content().json(objectMapper.writeValueAsString(respuesta)));
	}
	
	@Test
	void testListarCuentas() throws JsonProcessingException, Exception {
		List<Cuenta> cuentas = Arrays.asList(Datos.crearCuenta001().orElseThrow(),
				Datos.crearCuenta002().orElseThrow());
		when(cuentaServicio.findAll()).thenReturn(cuentas);
		
		mockMVC.perform(get("/api/cuentas").contentType(MediaType.APPLICATION_JSON))
//				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].persona").value("Cristian"))
				.andExpect(jsonPath("$[1].persona").value("Julio"))
				.andExpect(jsonPath("$[0].saldo").value("1000"))
				.andExpect(jsonPath("$[1].saldo").value("2000"))
				.andExpect(jsonPath("$",hasSize(2)))
				.andExpect(content().json(objectMapper.writeValueAsString(cuentas)));

			verify(cuentaServicio).findAll();

		}
	
	@Test
	void guardarCuenta() throws JsonProcessingException, Exception {
		Cuenta cuenta = new Cuenta(null, "Biaggio", new BigDecimal("3000"));
		when(cuentaServicio.save(any())).then(invocation -> {
			Cuenta c = invocation.getArgument(0);
			c.setId(3L);
			return c;
		});
		
		mockMVC.perform(post("/api/cuentas").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(cuenta)))
//		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.id",is(3)))
		.andExpect(jsonPath("$.persona",is("Biaggio")))
		.andExpect(jsonPath("$.saldo",is(3000)));
		
		verify(cuentaServicio).save(any());
		
	}
	
	public CuentaControladorTest() {
		// TODO Auto-generated constructor stub
	}

}
