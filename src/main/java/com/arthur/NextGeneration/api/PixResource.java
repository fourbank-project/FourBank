package com.arthur.NextGeneration.api;


import com.arthur.NextGeneration.model.entities.Conta;
import com.arthur.NextGeneration.model.entities.Pix;
import com.arthur.NextGeneration.model.entities.Pix;
import com.arthur.NextGeneration.model.services.ContaService;
import com.arthur.NextGeneration.model.services.PixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/pixs")
public class PixResource implements Serializable {

    @Autowired
    private PixService service;

    @Autowired
    private ContaService contaService;

    @GetMapping
    public ResponseEntity<List<Pix>> findAll() {
        List<Pix> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @PostMapping("/create")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Pix> novoPix(@RequestBody Pix pix) {
        service.createPix(pix);
        return ResponseEntity.status(HttpStatus.CREATED).body(pix);
    }

    @GetMapping(value = "/read/{id}")
    public ResponseEntity<Pix> findById(@PathVariable Long id) {
        Pix obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PutMapping("/update/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Pix> updatePix(@RequestBody Pix pix, @PathVariable Long id) {
        Pix pix1 = service.findById(id);
        if(pix1 == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(pix);
        }
        if(pix.getChavePix() != null){
            pix1.setChavePix(pix.getChavePix());
        }
        if(pix.getValor() != null){
            pix1.setValor(pix.getValor());
        }
        if(pix.getConta() != null){
            pix1.setConta(pix.getConta());
        }
        if(pix.getConteudoChave() != null){
            pix1.setConteudoChave(pix.getConteudoChave());
        }

        service.createPix(pix1);
        return ResponseEntity.status(HttpStatus.OK).body(pix1);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Pix> deletePix(@PathVariable Long id) {
        Pix pix = service.findById(id);
        if(service.deletePixById(id)) {
            return ResponseEntity.status(HttpStatus.OK).body(pix);
        } else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(pix);
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<Pix> pix(@PathVariable Long id, @RequestBody Pix pix, UriComponentsBuilder ucb){

        // Procurar por PIX baseado na chave
        Pix pixKey = service.findByConteudoChave(pix.getConteudoChave());

        // Valida se o pix que veio do é null
        if(pixKey == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        // Pega a conta associada com o PIX
        Conta contaDestino = pixKey.getConta();

        // Pega o id e pesquisar a conta de origem
        Conta contaOrigem = contaService.findById(id);

        // Valida se o saldo é maior que o valor a ser transferido
        if(contaOrigem != null && contaOrigem.getSaldo() >= pix.getValor()){
            if(service.transferirPix(contaOrigem,contaDestino,pix.getValor())){
                return ResponseEntity.status(HttpStatus.OK).build();
            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

        }else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}


