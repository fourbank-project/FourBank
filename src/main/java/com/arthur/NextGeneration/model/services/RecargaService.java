package com.arthur.NextGeneration.model.services;

import com.arthur.NextGeneration.model.entities.Conta;
import com.arthur.NextGeneration.model.entities.Recarga;
import com.arthur.NextGeneration.model.exceptions.ResourceNotFoundException;
import com.arthur.NextGeneration.model.repositories.RecargaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecargaService {

    @Autowired
    private RecargaRepository repository;

    @Autowired
    private ContaService service;

    public ResponseEntity<Recarga> save(Recarga r, Long id){
        Conta found = service.findById(id);
        if(found.getSaldo() >= r.getValor() && found != null){
            found.setSaldo(found.getSaldo() - r.getValor());
            r.setConta(found);
            found.getList().add(r);
            Recarga save = repository.save(r);
            return ResponseEntity.ok().body(save);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // Delete
    public ResponseEntity<Recarga> deleteRechargeById(Long id){
        Recarga found = findById(id);
        repository.delete(found);
        return ResponseEntity.ok().build();
    }

    public Recarga findById(Long id){
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Objeto nao encontrado"));
    }

    public List<Recarga> findAllById(Long id){
        return repository.findAllByContaId(id);
    }

    public List<Recarga> findAll(){
        return repository.findAll();
    }

}
