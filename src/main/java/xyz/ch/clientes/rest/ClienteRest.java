package xyz.ch.clientes.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xyz.ch.clientes.model.Cliente;
import xyz.ch.clientes.service.ClienteService;

import com.google.gson.Gson;

@RestController
@RequestMapping("/clientes")
public class ClienteRest {

	@Autowired
	private ClienteService clienteService;

	@GetMapping
	private ResponseEntity<Respuesta> getAllClientes() {
		Respuesta resp = new Respuesta(null, clienteService.findAll());
		return ResponseEntity.ok(resp);

	}

	@PostMapping
	private ResponseEntity<Respuesta> saveClientes(@RequestBody Cliente cliente) throws IOException, JAXBException {

		
		
		if (cliente.getGrupo().equals("sk")) {
			String json = obtenerDatosJson();
			DataJson data = new Gson().fromJson(json, DataJson.class);

			List<Cliente> clientesGuardados = clienteService.findAll();
			clientesGuardados = clientesGuardados.stream().filter(cli -> cli.getGrupo().equals(cliente.getGrupo()))
					.collect(Collectors.toList());
			
			Cliente clienteDuplicado = clientesGuardados.stream().filter(cli-> cli.getEmail().equals(cliente.getEmail())).findFirst().orElse(null);
			
			if (clienteDuplicado != null) {
				Respuesta resp = new Respuesta("Cliente ya registrado en esta promocion", null);
				return ResponseEntity.ok(resp);
			}
			
			if (clientesGuardados.isEmpty() || clientesGuardados.isEmpty()) {
				cliente.setBonificacion(data.getSk_formato().get(0).getBeneficio());
				Cliente clienteGuardado = clienteService.save(cliente);
				Respuesta resp = new Respuesta(null, clienteGuardado);
				return ResponseEntity.ok(resp);
			} else {
				
				int maximoDescuento = 0;
				List<Integer> listaDescuentos = new ArrayList<>();
				int i = 0;
				for (i = 0; i < clientesGuardados.size(); i++) {
					String beneficio = clientesGuardados.get(i).getBonificacion();
					String[] arrOfStr = beneficio.split("Descuento", 2);
					listaDescuentos.add(Integer.valueOf(arrOfStr[1]));
				}

				maximoDescuento = listaDescuentos.stream().mapToInt(x -> x).max().orElse(0);
				if (maximoDescuento < data.getSk_formato().size()) {
					cliente.setBonificacion(data.getSk_formato().get(maximoDescuento).getBeneficio());
					Cliente clienteGuardado = clienteService.save(cliente);
					Respuesta resp = new Respuesta(null, clienteGuardado);
					return ResponseEntity.ok(resp);
				} else {
					Respuesta resp = new Respuesta("No existe promociones disponibles", null);
					return ResponseEntity.ok(resp);
				}
			}
		} else if (cliente.getGrupo().equals("th")) {
			DataXml  data =  obtenerDatosXml();
			//System.out.println(data.getBeneficios().get(0).getBeneficio().get(0));
			
			List<Cliente> clientesGuardados = clienteService.findAll();
			clientesGuardados = clientesGuardados.stream().filter(cli -> cli.getGrupo().equals(cliente.getGrupo()))
					.collect(Collectors.toList());
			Cliente clienteDuplicado = clientesGuardados.stream().filter(cli-> cli.getEmail().equals(cliente.getEmail())).findFirst().orElse(null);
			
			if (clienteDuplicado != null) {
				Respuesta resp = new Respuesta("Cliente ya registrado en esta promocion", null);
				return ResponseEntity.ok(resp);
			}
			if (clientesGuardados.isEmpty() || clientesGuardados.isEmpty()) {
				cliente.setBonificacion(data.getBeneficios().get(0).getBeneficio().get(0));
				Cliente clienteGuardado = clienteService.save(cliente);
				Respuesta resp = new Respuesta(null, clienteGuardado);
				return ResponseEntity.ok(resp);
			} else {
				
				int maximoDescuento = 0;
				List<Integer> listaDescuentos = new ArrayList<>();
				int i = 0;
				for (i = 0; i < clientesGuardados.size(); i++) {
					String beneficio = clientesGuardados.get(i).getBonificacion();
					String[] arrOfStr = beneficio.split("Descuento", 2);
					listaDescuentos.add(Integer.valueOf(arrOfStr[1]));
				}

				maximoDescuento = listaDescuentos.stream().mapToInt(x -> x).max().orElse(0);
				if (maximoDescuento < data.getBeneficios().get(0).getBeneficio().size()) {
					cliente.setBonificacion(data.getBeneficios().get(0).getBeneficio().get(maximoDescuento));
					Cliente clienteGuardado = clienteService.save(cliente);
					Respuesta resp = new Respuesta(null, clienteGuardado);
					return ResponseEntity.ok(resp);
				} else {
					Respuesta resp = new Respuesta("No existe promociones disponibles", null);
					return ResponseEntity.ok(resp);
				}
			}
			
			
		} else {
			Respuesta resp = new Respuesta("Grupo invalido", null);// clienteService.save(cliente));
			return ResponseEntity.ok(resp);
		}

		// System.out.println(data.getSk_formato().get(0).getBeneficio());

	}

	private String obtenerDatosJson() throws IOException {
		URL url = new URL(
				"https://raw.githubusercontent.com/SistemasComoHogar/ClientesBack/main/Referencias/sk_formato.json");
		URLConnection connection = url.openConnection();
		try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
			String line;
			String resp = "";
			while ((line = in.readLine()) != null) {
				resp += line;
			}
			return resp;
		}
	}
	
	private DataXml obtenerDatosXml() throws IOException, JAXBException {
		URL url = new URL(
				"https://raw.githubusercontent.com/SistemasComoHogar/ClientesBack/main/Referencias/th_formato.xml");
		URLConnection connection = url.openConnection();
		String resp = "";
		try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
			String line;
			
			while ((line = in.readLine()) != null) {
				//System.out.println(line);
				resp += line;
			}
			
		}

		JAXBContext jaxbContext = JAXBContext.newInstance(DataXml.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		StringReader reader = new StringReader(resp);
		DataXml data = (DataXml) unmarshaller.unmarshal(reader);
		return data;
	}
	

}
