package xyz.ch.clientes.rest;

import org.springframework.stereotype.Controller;

@Controller
public class Respuesta {
	
	private String errorMsg;
	private Object obj;
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	public Respuesta(String errorMsg, Object obj) {
		super();
		this.errorMsg = errorMsg;
		this.obj = obj;
	}
	public Respuesta() {
		super();
	}
	
	

}