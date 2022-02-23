package com.arthur.NextGeneration.api;


import com.arthur.NextGeneration.model.entities.Conta;
import com.arthur.NextGeneration.model.entities.Conta;
import com.arthur.NextGeneration.model.entities.Pix;
import com.arthur.NextGeneration.model.services.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@RestController
@RequestMapping(value = "/api/contas")
public class ContaResource implements Serializable {

    @Autowired
    private ContaService service;

    @GetMapping
    public ResponseEntity<List<Conta>> findAll() {
        List<Conta> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @PostMapping("/create")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Conta> novoConta(@RequestBody Conta conta) {
        service.createConta(conta);
        return ResponseEntity.status(HttpStatus.CREATED).body(conta);
    }

    @GetMapping(value = "/read/{id}")
    public ResponseEntity<Conta> findById(@PathVariable Long id) {
        Conta obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PutMapping("/update/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Conta> updateConta(@RequestBody Conta conta, @PathVariable Long id) {
        Conta conta1 = service.findById(id);
        if(conta1 == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(conta);
        }
        if(conta.getSaldo() != null){
            conta1.setSaldo(conta.getSaldo());
        }
        if(conta.getTipoConta() != null){
            conta1.setTipoConta(conta.getTipoConta());
        }
        if(conta.getSaldo() != null){
            conta1.setSaldo(conta.getSaldo());
        }
        if(conta.getSenha() != null){
            conta1.setSenha(conta.getSenha());
        }
        if(conta.getCartaoCredito() != null){
            conta1.setCartaoCredito(conta.getCartaoCredito());
        }
        if(conta.getCartaoDebito() != null){
            conta1.setCartaoDebito(conta.getCartaoDebito());
        }
        if(conta.getCliente() != null){
            conta1.setCliente(conta.getCliente());
        }
        if(conta.getTaxa() != null){
            conta1.setTaxa(conta.getTaxa());
        }

        service.createConta(conta1);
        return ResponseEntity.status(HttpStatus.OK).body(conta1);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Conta> deleteConta(@PathVariable Long id) {
        Conta conta = service.findById(id);
        if(service.deleteContaById(id)) {
            return ResponseEntity.status(HttpStatus.OK).body(conta);
        } else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(conta);
        }
    }

    @GetMapping(value = "/{email}/{senha}")
    public ResponseEntity<Conta> findById(@PathVariable(value = "email") String email,@PathVariable(value = "senha") String senha) {
        Conta obj = service.findBySenhaAndEmail(senha,email);
        System.err.println(obj.getId());
        return ResponseEntity.ok().body(obj);
    }
}
