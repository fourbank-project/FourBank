package com.arthur.NextGeneration.api;


import com.arthur.NextGeneration.model.entities.Endereco;
import com.arthur.NextGeneration.model.entities.Endereco;
import com.arthur.NextGeneration.model.services.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@RestController
@RequestMapping(value = "/api/enderecos")
public class EnderecoResource implements Serializable {

    @Autowired
    private EnderecoService service;

    @GetMapping
    public ResponseEntity<List<Endereco>> findAll() {
        List<Endereco> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @PostMapping("/create")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Endereco> novoEndereco(@RequestBody Endereco endereco) {
        service.createEndereco(endereco);
        return ResponseEntity.status(HttpStatus.CREATED).body(endereco);
    }

    @GetMapping(value = "/read/{id}")
    public ResponseEntity<Endereco> findById(@PathVariable Long id) {
        Endereco obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PutMapping("/update/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Endereco> updateEndereco(@RequestBody Endereco endereco, @PathVariable Long id) {
        Endereco endereco1 = service.findById(id);
        if(endereco1 == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(endereco);
        }
        if(endereco.getBairro() != null){
            endereco1.setBairro(endereco.getBairro());
        }
        if(endereco.getCep() != null){
            endereco1.setCep(endereco.getCep());
        }
        if(endereco.getCidade() != null){
            endereco1.setCidade(endereco.getCidade());
        }
        if(endereco.getEstado() != null){
            endereco1.setEstado(endereco.getEstado());
        }
        if(endereco.getLogradouro() != null){
            endereco1.setLogradouro(endereco.getLogradouro());
        }
        if(endereco.getNumero() != null){
            endereco1.setNumero(endereco.getNumero());
        }

        service.createEndereco(endereco1);
        return ResponseEntity.status(HttpStatus.OK).body(endereco1);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Endereco> deleteEndereco(@PathVariable Long id) {
        Endereco endereco = service.findById(id);
        if(service.deleteEnderecoById(id)) {
            return ResponseEntity.status(HttpStatus.OK).body(endereco);
        } else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(endereco);
        }
    }
}
