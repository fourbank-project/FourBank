package com.arthur.NextGeneration.api;

import com.arthur.NextGeneration.model.entities.Emprestimo;
import com.arthur.NextGeneration.model.services.EmprestimoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/emprestimo")
public class EmprestimoResource {

    @Autowired
    EmprestimoService emprestimoService;

    @PostMapping
    public ResponseEntity<Emprestimo> doLoan(@RequestBody Emprestimo emprestimo) {
        Emprestimo created = emprestimoService.save(emprestimo);
        return (created == null) ? ResponseEntity.badRequest().build() : ResponseEntity.ok().body(created);
    }

    // (condicao) ? (se for verdade) : (se for falso) <<<<<< TERNÁRIO
    @GetMapping("{id}")
    public ResponseEntity<Emprestimo> findById(@PathVariable long id) {
        Emprestimo emprestimo = emprestimoService.findById(id);
        return (emprestimo != null) ? ResponseEntity.ok().body(emprestimo) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Emprestimo>> findAll() {
        List<Emprestimo> list = emprestimoService.findAll();
        return ResponseEntity.ok().body(list);
    }
}
