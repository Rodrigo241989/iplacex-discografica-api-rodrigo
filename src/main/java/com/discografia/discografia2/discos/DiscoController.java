package com.discografia.discografia2.discos;

import com.discografia.discografia2.artistas.IArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class DiscoController {

    @Autowired
    private IDiscoRepository discoRepository;

    @Autowired
    private IArtistaRepository artistaRepository;

    // POST
    @PostMapping(value = "/disco",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Disco> crear(@RequestBody Disco disco) {

        if (!artistaRepository.existsById(disco.idArtista)) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(discoRepository.save(disco));
    }

    // GET ALL
    @GetMapping(value = "/discos",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Disco>> listar() {
        return ResponseEntity.ok(discoRepository.findAll());
    }

    // GET BY ID
    @GetMapping(value = "/disco/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Disco> obtener(@PathVariable String id) {
        return discoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET DISCOS POR ARTISTA
    @GetMapping(value = "/artista/{id}/discos",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Disco>> obtenerPorArtista(@PathVariable String id) {
        return ResponseEntity.ok(discoRepository.findDiscosByIdArtista(id));
    }
}
