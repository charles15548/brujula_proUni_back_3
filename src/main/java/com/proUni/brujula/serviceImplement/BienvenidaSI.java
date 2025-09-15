package com.proUni.brujula.serviceImplement;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.proUni.brujula.models.Bienvenida;
import com.proUni.brujula.models.DesarrolloPersonal;
import com.proUni.brujula.models.Noticias;
import com.proUni.brujula.repository.BienvenidaRepository;
import com.proUni.brujula.repository.DesarrolloPersonalRepository;
import com.proUni.brujula.service.BienvenidaService;
import com.proUni.brujula.service.DesarrolloPersonalService;

@Service
public class BienvenidaSI implements BienvenidaService{

	@Autowired
	private BienvenidaRepository dao;
	




	@Override
	public ResponseEntity<Map<String, Object>> listarBienvenida() {
		Map<String,Object> respuesta = new HashMap<>();	
		List<Bienvenida> b = dao.findAll();
		
		if(!b.isEmpty()) {
			respuesta.put("mensaje", "Lista");
			respuesta.put("bienvenida", b);
			respuesta.put("status", HttpStatus.OK);
			respuesta.put("fecha", new Date());	
			return ResponseEntity.status(HttpStatus.OK).body(respuesta);
		}else {
			respuesta.put("mensaje", "No existen registros DP");
			respuesta.put("status", HttpStatus.NOT_FOUND);
			respuesta.put("fecha", new Date());	
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
		}
	}


	@Override
	public ResponseEntity<Map<String, Object>> crearBienvenida(String titulo, String descripcion) {
		Map<String,Object> respuesta = new HashMap<>();	
		try {
			Bienvenida b = new Bienvenida(titulo,descripcion);
			Bienvenida nueva = dao.save(b);
			respuesta.put("mensaje", "creada con éxito");
            respuesta.put("bienvenida", nueva);
			return ResponseEntity.ok(respuesta);
		}catch (Exception e) {
            respuesta.put("mensaje", "Error: " + e.getMessage());
            return ResponseEntity.badRequest().body(respuesta);
		}
	}


	@Override
	public ResponseEntity<Map<String, Object>> editarBienvenida(Long id, String titulo, String descripcion) {
		Map<String,Object> respuesta = new HashMap<>();	
		Optional<Bienvenida> existe = dao.findById(id);
		if(existe.isPresent()) {
			Bienvenida b = existe.get();
			
			b.setTitulo(titulo);
			b.setDescripcion(descripcion);
			dao.save(b);
			
			respuesta.put("mensaje", "Actualizada con éxito");
            respuesta.put("bienvenida", b);
            respuesta.put("status", HttpStatus.OK.value());
            respuesta.put("fecha", new Date());
            return ResponseEntity.ok(respuesta);
		}else {
			respuesta.put("mensaje", "No se encontró para actualizar");
            respuesta.put("status", HttpStatus.NOT_FOUND.value());
            respuesta.put("fecha", new Date());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
		}
	}


	@Override
	public ResponseEntity<Map<String, Object>> eliminarBienvenida(Long id) {
		Map<String,Object> respuesta = new HashMap<>();	
		Optional<Bienvenida> existe = dao.findById(id);
        if(existe.isPresent()) {
        	Bienvenida b = existe.get();
        	dao.delete(b);
        	respuesta.put("mensaje", "Eliminada con éxito");
            respuesta.put("status", HttpStatus.OK.value());
            respuesta.put("fecha", new Date());
            return ResponseEntity.ok(respuesta);
        }else {
        	respuesta.put("mensaje", "No se encontró para eliminar");
            respuesta.put("status", HttpStatus.NOT_FOUND.value());
            respuesta.put("fecha", new Date());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
	}

	 

}