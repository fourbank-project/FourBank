package com.arthur.NextGeneration.api;


import com.arthur.NextGeneration.model.entities.Seguro;
import com.arthur.NextGeneration.model.entities.Seguro;
import com.arthur.NextGeneration.model.services.SeguroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@RestController
@RequestMapping(value = "/api/segurosDados")
public class SeguroResource implements Serializable {

    @Autowired
    private SeguroService service;

    @GetMapping
    public ResponseEntity<List<Seguro>> findAll() {
        List<Seguro> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @PostMapping("/create")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Seguro> novoSeguro(@RequestBody Seguro seguro) {
        service.createSeguro(seguro);
        return ResponseEntity.status(HttpStatus.CREATED).body(seguro);
    }

    @GetMapping(value = "/read/{id}")
    public ResponseEntity<Seguro> findById(@PathVariable Long id) {
        Seguro obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PutMapping("/update/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Seguro> updateSeguro(@RequestBody Seguro seguro, @PathVariable Long id) {
        Seguro seguro1 = service.findById(id);
        if(seguro1 == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(seguro);
        }
        if(seguro.getValorAno() != null){
            seguro1.setValorAno(seguro.getValorAno());
        }
        if(seguro.getNome() != null){
            seguro1.setNome(seguro.getNome());
        }
        if(seguro.getRegras() != null){
            seguro1.setRegras(seguro.getRegras());
        }

        service.createSeguro(seguro1);
        return ResponseEntity.status(HttpStatus.OK).body(seguro1);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Seguro> deleteSeguro(@PathVariable Long id) {
        Seguro seguro = service.findById(id);
        if(service.deleteSeguroById(id)) {
            return ResponseEntity.status(HttpStatus.OK).body(seguro);
        } else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(seguro);
        }
    }
}
