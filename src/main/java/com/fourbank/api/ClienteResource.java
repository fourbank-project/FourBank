package com.fourbank.api;


import com.fourbank.model.entities.Cliente;
import com.fourbank.model.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@RestController
@RequestMapping(value = "/api/clientes")
public class ClienteResource implements Serializable {

    @Autowired
    private ClienteService service;

    @GetMapping
    public ResponseEntity<List<Cliente>> findAll() {
        List<Cliente> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/read/{id}")
    public ResponseEntity<Cliente> findById(@PathVariable Long id) {
        Cliente obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<Cliente> novoCliente(@RequestBody Cliente cliente) {
        service.createCliente(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
    }

    @PutMapping("/update/{id}")
    @ResponseBody
    public ResponseEntity<Cliente> updateCliente(@RequestBody Cliente cliente, @PathVariable Long id) {
        Cliente cliente1 = service.findById(id);
        if (cliente1 == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(cliente);
        }
        if (cliente.getCpf() != null) {
            cliente1.setCpf(cliente.getCpf());
        }
        if (cliente.getNome() != null) {
            cliente1.setNome(cliente.getNome());
        }
        if (cliente.getEmail() != null) {
            cliente1.setEmail(cliente.getEmail());
        }
        if (cliente.getTelefone() != null) {
            cliente1.setTelefone(cliente.getTelefone());
        }
        if (cliente.getEndereco() != null) {
            cliente1.setEndereco(cliente.getEndereco());
        }
        if (cliente.getDataDeNascimento() != null) {
            cliente1.setDataDeNascimento(cliente.getDataDeNascimento());
        }
        if (cliente.getTipo() != null) {
            cliente1.setTipo(cliente.getTipo());
        }
        service.createCliente(cliente1);
        return ResponseEntity.status(HttpStatus.OK).body(cliente1);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<Cliente> deleteCliente(@PathVariable Long id) {
        if (service.deleteClienteById(id)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
