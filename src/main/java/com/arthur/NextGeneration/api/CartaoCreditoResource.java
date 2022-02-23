package com.arthur.NextGeneration.api;


import com.arthur.NextGeneration.model.entities.CartaoCredito;
import com.arthur.NextGeneration.model.entities.CartaoCredito;
import com.arthur.NextGeneration.model.services.CartaoCreditoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@RestController
@RequestMapping(value = "/api/cartaocreditos")
public class CartaoCreditoResource implements Serializable {

    @Autowired
    private CartaoCreditoService service;

    @GetMapping
    public ResponseEntity<List<CartaoCredito>> findAll() {
        List<CartaoCredito> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @PostMapping("/create")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CartaoCredito> novoCartaoCredito(@RequestBody CartaoCredito cartaoCredito) {
        service.createCartaoCredito(cartaoCredito);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartaoCredito);
    }

    @GetMapping(value = "/read/{id}")
    public ResponseEntity<CartaoCredito> findById(@PathVariable Long id) {
        CartaoCredito obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PutMapping("/update/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CartaoCredito> updateCartaoCredito(@RequestBody CartaoCredito cartaoCredito, @PathVariable Long id) {
        CartaoCredito cartaoCredito1 = service.findById(id);
        if(cartaoCredito1 == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(cartaoCredito);
        }
        if(cartaoCredito.getApolices() != null){
            cartaoCredito1.setApolices(cartaoCredito.getApolices());
        }
        if(cartaoCredito.getValorFatura() != null){
            cartaoCredito1.setValorFatura(cartaoCredito.getValorFatura());
        }
        if(cartaoCredito.getCompras() != null){
            cartaoCredito1.setCompras(cartaoCredito.getCompras());
        }
        if(cartaoCredito.getLimite() != null){
            cartaoCredito1.setLimite(cartaoCredito.getLimite());
        }
        if(cartaoCredito.getDataVencimento() != null){
            cartaoCredito1.setDataVencimento(cartaoCredito.getDataVencimento());
        }
        if(cartaoCredito.getBandeira() != null){
            cartaoCredito1.setBandeira(cartaoCredito.getBandeira());
        }
        if(cartaoCredito.getNumero() != null){
            cartaoCredito1.setNumero(cartaoCredito.getNumero());
        }
        if(cartaoCredito.getSenha() != null){
            cartaoCredito1.setSenha(cartaoCredito.getSenha());
        }

        service.createCartaoCredito(cartaoCredito1);
        return ResponseEntity.status(HttpStatus.OK).body(cartaoCredito1);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CartaoCredito> deleteCartaoCredito(@PathVariable Long id) {
        CartaoCredito cartaoCredito = service.findById(id);
        if(service.deleteCartaoCreditoById(id)) {
            return ResponseEntity.status(HttpStatus.OK).body(cartaoCredito);
        } else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(cartaoCredito);
        }
    }
}
