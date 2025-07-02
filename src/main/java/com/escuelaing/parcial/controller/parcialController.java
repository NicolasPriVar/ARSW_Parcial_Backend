package com.escuelaing.parcial.controller;

import com.escuelaing.parcial.adapter.AlphaAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/indicador")
public class parcialController {

    @Autowired
    AlphaAdapter adaptador;

    @PostMapping
    public String recibirMensaje(@RequestBody Map<String, String> mensaje) {
        return adaptador.generateResponse(mensaje.get("indicador"));
    }


    @PostMapping("/indicador")
    public ResponseEntity<Map<String, String>> ingresarPartida(@RequestBody Map<String, String> body) {
        String indicador = body.get("indicador");

        if (indicador == null || indicador.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Datos inválidos"));
        }

        return ResponseEntity.ok(Map.of("message", "Bienvenido"));
    }

}
