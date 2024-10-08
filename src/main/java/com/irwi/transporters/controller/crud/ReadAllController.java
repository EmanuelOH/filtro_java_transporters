package com.irwi.transporters.controller.crud;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReadAllController <Entity>{
    public ResponseEntity<List<Entity>> readAll();
}
