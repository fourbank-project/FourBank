package com.arthur.NextGeneration.api;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arthur.NextGeneration.model.entities.Conta;
import com.arthur.NextGeneration.model.services.ContaService;

@RestController
@RequestMapping("/api/login")
public class LoginResource implements Serializable {
    @Autowired
    private ContaService service;

    @GetMapping("/{email}/{senha}")
    public ResponseEntity<Conta> findById(@PathVariable(value = "email") String email,@PathVariable(value = "senha") String senha) {
        Conta obj = service.findBySenhaAndEmail(senha,email);
        if(obj != null){
            return ResponseEntity.ok().body(obj);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
