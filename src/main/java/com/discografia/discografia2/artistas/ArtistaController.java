package com.discografia.discografia2.artistas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping("/api")
public class ArtistaController {
    @Autowired
    private IArtistaRepository artistaRepository;

    // GET ALL
    @GetMapping(value = "/artistas",
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Artista>> HandleGetAristasRequest() {
        return ResponseEntity.ok(artistaRepository.findAll());
    }

    // POST
    @PostMapping(value = "/artistas",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Artista> HandleInsertArtistaRequest(@RequestBody Artista artista) {
        return ResponseEntity.ok(artistaRepository.save(artista));
    }

    // GET BY ID
    @GetMapping(value = "/artistas/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Artista> HandleGetArtistaRequest(@PathVariable String id) {
        return artistaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Modificar un artista (PUT)
    @PutMapping(value = "/artistas/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Artista> HandleUpdateArtistaRequest(@PathVariable String id, @RequestBody Artista artista) {
        
        if (!artistaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        artista.id = id;
        return ResponseEntity.ok(artistaRepository.save(artista));
    }

    // Borrar un artista (DELETE)
    @DeleteMapping("/artistas/{id}")
    public ResponseEntity<Void> HandleUpdateArtistaRequest(@PathVariable String id) {
        
        if (!artistaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        artistaRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
