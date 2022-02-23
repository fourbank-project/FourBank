package com.arthur.NextGeneration.api;


import com.arthur.NextGeneration.model.entities.CartaoDebito;
import com.arthur.NextGeneration.model.entities.CartaoDebito;
import com.arthur.NextGeneration.model.services.CartaoDebitoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@RestController
@RequestMapping(value = "/api/cartaodebitos")
public class CartaoDebitoResource implements Serializable {

    @Autowired
    private CartaoDebitoService service;

    @GetMapping
    public ResponseEntity<List<CartaoDebito>> findAll() {
        List<CartaoDebito> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @PostMapping("/create")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CartaoDebito> novoCartaoDebito(@RequestBody CartaoDebito cartaoDebito) {
        service.createCartaoDebito(cartaoDebito);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartaoDebito);
    }

    @GetMapping(value = "/read/{id}")
    public ResponseEntity<CartaoDebito> findById(@PathVariable Long id) {
        CartaoDebito obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PutMapping("/update/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CartaoDebito> updateCartaoDebito(@RequestBody CartaoDebito cartaoDebito, @PathVariable Long id) {
        CartaoDebito cartaoDebito1 = service.findById(id);
        if(cartaoDebito1 == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(cartaoDebito);
        }
        if(cartaoDebito.getSenha() != null){
            cartaoDebito1.setSenha(cartaoDebito.getSenha());
        }
        if(cartaoDebito.getLimitePorTransacao() != null){
            cartaoDebito1.setLimitePorTransacao(cartaoDebito.getLimitePorTransacao());
        }
        if(cartaoDebito.getBandeira() != null){
            cartaoDebito1.setBandeira(cartaoDebito.getBandeira());
        }
        if(cartaoDebito.getNumero() != null){
            cartaoDebito1.setNumero(cartaoDebito.getNumero());
        }

        service.createCartaoDebito(cartaoDebito1);
        return ResponseEntity.status(HttpStatus.OK).body(cartaoDebito1);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CartaoDebito> deleteCartaoDebito(@PathVariable Long id) {
        CartaoDebito cartaoDebito = service.findById(id);
        if(service.deleteCartaoDebitoById(id)) {
            return ResponseEntity.status(HttpStatus.OK).body(cartaoDebito);
        } else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(cartaoDebito);
        }
    }
}
