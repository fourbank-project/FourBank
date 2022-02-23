package com.arthur.NextGeneration.model.services;

import com.arthur.NextGeneration.model.entities.Emprestimo;
import com.arthur.NextGeneration.model.repositories.EmprestimoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EmprestimoService {

    @Autowired
    EmprestimoRepository repository;

    // save
    public Emprestimo save(Emprestimo emprestimo) {
        return repository.save(emprestimo);
    }

    //find all
    public List<Emprestimo> findAll() {
        return repository.findAll();
    }

    //find by id
    public Emprestimo findById(Long id) {
        Optional<Emprestimo> optional = repository.findById(id);
        return optional.get();
    }

    // Create/Update
    public boolean createEmprestimo(Emprestimo emprestimo){
        if(emprestimo != null){
            repository.save(emprestimo);
            return true;
        }
        return false;
    }

    // Delete
    public boolean deleteEmprestimoById(Long id){
        Optional<Emprestimo> emprestimoToDelete = repository.findById(id);
        if(emprestimoToDelete.isPresent()){
            repository.delete(emprestimoToDelete.get());
            return true;
        }
        return false;
    }
}