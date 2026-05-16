package com.discografia.discografia2.discos;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "discos")
public class Disco {

    @Id
    public String id;

    public String idArtista;
    public String nombre;
    public int anioLanzamiento;
    public List<String> canciones;
}
