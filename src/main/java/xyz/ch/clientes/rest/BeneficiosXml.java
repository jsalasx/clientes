package xyz.ch.clientes.rest;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "beneficios2", propOrder = {
	    "beneficio"})
public class BeneficiosXml {

	
	@XmlElement(name="beneficio")
	private List<String> beneficio;

	public List<String> getBeneficio() {
		return beneficio;
	}

	public void setBeneficio(List<String> beneficio) {
		this.beneficio = beneficio;
	}

	
}
