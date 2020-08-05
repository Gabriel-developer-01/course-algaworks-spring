package com.modulo.projetoapi.event;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

//Classe responsável por receber os eventos de header Location e eliminar códigos duplicados.
public class RecursoCriadoEvent extends ApplicationEvent {
	private static final long serialVersionUID = 1L;

	private HttpServletResponse response;
	private Long codigo;

	// aqui é criado o evento devemos passar como parâmetro
	// Object,HttpServletResponse e o código.
	public RecursoCriadoEvent(Object source, HttpServletResponse response, Long codigo) {
		super(source);
		this.response = response;
		this.codigo = codigo;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public Long getCodigo() {
		return codigo;
	}
}
