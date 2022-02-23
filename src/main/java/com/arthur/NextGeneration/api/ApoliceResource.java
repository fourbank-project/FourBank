package com.arthur.NextGeneration.api;


import com.arthur.NextGeneration.model.entities.*;
import com.arthur.NextGeneration.model.entities.Apolice;
import com.arthur.NextGeneration.model.services.ApoliceService;
import com.arthur.NextGeneration.model.services.CartaoCreditoService;
import com.arthur.NextGeneration.model.services.ContaService;
import com.arthur.NextGeneration.model.services.SeguroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@RestController
@RequestMapping(value = "/api/apolices")
public class ApoliceResource implements Serializable{

    @Autowired
    private ApoliceService service;

    @Autowired
    private SeguroService seguroService;
    
    @Autowired
    private ContaService contaService;

    @GetMapping
    public ResponseEntity<List<Apolice>> findAll() {
        List<Apolice> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @PostMapping("/create")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Apolice> novoApolice(@RequestBody Apolice apolice) {
        service.createApolice(apolice);
        return ResponseEntity.status(HttpStatus.CREATED).body(apolice);
    }

    @GetMapping(value = "/read/{id}")
    public ResponseEntity<Apolice> findById(@PathVariable Long id) {
        Apolice obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PutMapping("/update/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Apolice> updateApolice(@RequestBody Apolice apolice, @PathVariable Long id) {
        Apolice apolice1 = service.findById(id);
        if(apolice1 == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apolice);
        }
        if(apolice.getValorApolice() != null){
            apolice1.setValorApolice(apolice.getValorApolice());
        }
        if(apolice.getDescricaoCondicoes() != null){
            apolice1.setDescricaoCondicoes(apolice.getDescricaoCondicoes());
        }
        if(apolice.getDataAssinatura() != null){
            apolice1.setDataAssinatura(apolice.getDataAssinatura());
        }
        if(apolice.getDataCarencia() != null){
            apolice1.setDataCarencia(apolice.getDataCarencia());
        }

        service.createApolice(apolice1);
        return ResponseEntity.status(HttpStatus.OK).body(apolice1);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Apolice> deleteApolice(@PathVariable Long id) {
        Apolice apolice = service.findById(id);
        if(service.deleteApoliceById(id)) {
            return ResponseEntity.status(HttpStatus.OK).body(apolice);
        } else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apolice);
        }
    }

    @PostMapping("/contratar/{idConta}/{idSeguro}")
    public ResponseEntity<Apolice> contratar(@PathVariable Long idConta, @PathVariable Long idSeguro){
        Seguro seguro = seguroService.findById(idSeguro);
        Conta conta = contaService.findById(idConta);
        Apolice apolice = new Apolice();
        apolice.setValorApolice(150.0);
        apolice.setSeguro(seguro);
        conta.getCartaoCredito().getApolices().add(apolice);
        service.createApolice(apolice);
        contaService.createConta(conta);
        return ResponseEntity.ok().body(apolice);
    }
}