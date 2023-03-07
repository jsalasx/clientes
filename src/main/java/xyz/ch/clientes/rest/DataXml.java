package xyz.ch.clientes.rest;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;






@XmlRootElement(name="th_formato")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "beneficios", propOrder = {
"beneficios"})
public class DataXml {
	
	@XmlElement(required = false)
	protected List<BeneficiosXml> beneficios;

	
	
	public List<BeneficiosXml> getBeneficios() {
		if (beneficios == null) {
			beneficios = new ArrayList<BeneficiosXml>();
		}
		return beneficios;
		
	}
	




	
}
