package ar.com.ada.api.billeteravirtual.controllers;

import java.util.List;

import javax.xml.ws.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.billeteravirtual.entities.*;
import ar.com.ada.api.billeteravirtual.services.PersonaService;

/**
 * SegundaController
 */
@RestController
public class PersonaController {

    @Autowired
    PersonaService personaService;

    /**
     * Este metodo tiene un queryString que viene por url y es opcional, este campo
     * servira para buscar las personas con un nombre especifico. ejemplo: /personas
     * -> devuelve la lista de todas las personas. En este caso la lista de personas
     * ordenadas usando el service+repo creado ejemplo2: /personas?nombre=Analia ->
     * trae todas las personas que tengan el nombre analia. la busqueda la hace a
     * traves del service+repo(query creado en repo)
     * 
     * @param nombre
     * @return
     */
    @GetMapping("/personas")
    public List<Persona> getPersonas(@RequestParam(value = "nombre", required = false) String nombre) {
        List<Persona> lp;

        if (nombre == null) {
            lp = personaService.buscarPersonasOrdenadoPorNombre();
        } else {
            lp = personaService.buscarTodosPorNombre(nombre);
        }

        return lp;
    }

    /**
     * Version devolviendo diferentes tipos de status segun se requiera se hace
     * usando la clase ResponseEntity. En este caso devuelve un "Ok(Persona)" o un ResponseEntity notfound (404)
     * El responseEntity se le debe pasar en el operador diamante el tipo de objeto a devolver.
     * @param id
     * @return
     */
    @GetMapping("/personas/{id}")
    public ResponseEntity<Persona> getPersonaById(@PathVariable int id) {
        Persona p = personaService.buscarPorId(id);

        if (p == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(p);
    }

}