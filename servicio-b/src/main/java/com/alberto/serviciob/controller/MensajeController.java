package com.alberto.serviciob.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mensaje")
public class MensajeController {

    @GetMapping
    public String mensaje() {
        return "Hola desde servicio B";
    }
}