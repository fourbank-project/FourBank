package com.arthur.NextGeneration.api;


import com.arthur.NextGeneration.model.entities.Compra;
import com.arthur.NextGeneration.model.entities.Compra;
import com.arthur.NextGeneration.model.services.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@RestController
@RequestMapping(value = "/api/compras")
public class CompraResource implements Serializable {

    @Autowired
    private CompraService service;

    @GetMapping
    public ResponseEntity<List<Compra>> findAll() {
        List<Compra> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @PostMapping("/create")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Compra> novoCompra(@RequestBody Compra compra) {
        service.createCompra(compra);
        return ResponseEntity.status(HttpStatus.CREATED).body(compra);
    }

    @GetMapping(value = "/read/{id}")
    public ResponseEntity<Compra> findById(@PathVariable Long id) {
        Compra obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PutMapping("/update/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Compra> updateCompra(@RequestBody Compra compra, @PathVariable Long id) {
        Compra compra1 = service.findById(id);
        if(compra1 == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(compra);
        }
        if(compra.getCompra() != null){
            compra1.setCompra(compra.getCompra());
        }
        if(compra.getValor() != null){
            compra1.setValor(compra.getValor());
        }
        if(compra.getNomeProduto() != null){
            compra1.setNomeProduto(compra.getNomeProduto());
        }

        service.createCompra(compra1);
        return ResponseEntity.status(HttpStatus.OK).body(compra1);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Compra> deleteCompra(@PathVariable Long id) {
        Compra compra = service.findById(id);
        if(service.deleteCompraById(id)) {
            return ResponseEntity.status(HttpStatus.OK).body(compra);
        } else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(compra);
        }
    }
}
