package com.arthur.NextGeneration.api;

import com.arthur.NextGeneration.model.entities.Recarga;
import com.arthur.NextGeneration.model.services.RecargaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/recarga")
public class RecargaResource {

    @Autowired
    private RecargaService service;

    @PostMapping("/create/{id}")
    public ResponseEntity<Recarga> doRecharge(@RequestBody Recarga recarga, @PathVariable Long id) {
        return service.save(recarga,id);
    }

    @GetMapping("{id}")
    public ResponseEntity<Recarga> getRecharge(@RequestBody Recarga recarga, @PathVariable Long id) {
        return service.save(recarga,id);
    }

    @GetMapping
    public ResponseEntity<List<Recarga>> mapAllRecargas(){
        List<Recarga> all = service.findAll();
        return ResponseEntity.ok().body(all);
    }

    @GetMapping("/recargas/{id}")
    public ResponseEntity<List<Recarga>> getAllRecharges(@PathVariable Long id){
        try {
            List<Recarga> recargas = service.findAllById(id);
            if(!recargas.isEmpty()){
                return ResponseEntity.ok().body(recargas);
            }else{
                return ResponseEntity.notFound().build();
            }
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
