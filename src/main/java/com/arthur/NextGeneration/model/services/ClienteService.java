package com.arthur.NextGeneration.model.services;

import com.arthur.NextGeneration.model.entities.*;
import com.arthur.NextGeneration.model.entities.Cliente;
import com.arthur.NextGeneration.model.enums.TipoChavePix;
import com.arthur.NextGeneration.model.enums.TipoConta;
import com.arthur.NextGeneration.model.repositories.ClienteRepository;
import com.arthur.NextGeneration.model.repositories.ContaRepository;
import com.arthur.NextGeneration.model.repositories.PixRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class
ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    PixRepository pixRepository;

    @Autowired
    ContaRepository contaRepository;

    @Autowired
    private PixService service;

    private Cliente cliente = new Cliente();

    public ClienteService(){}

    public ClienteService(String cpf, String nome, Date dataDeNascimento, Endereco endereco, String email, String telefone) {
        cadastrarDados(nome, cpf, dataDeNascimento, endereco, email, telefone);
    }

    public ClienteService(Cliente cliente) {
        this.cliente = cliente;
    }

    // Search Methods

    public List<Cliente> findAll() {
        return repository.findAll();
    }

    public Cliente findById(Long id) {
        Optional<Cliente> optional = repository.findById(id);
        return optional.get();
    }

    // Validation Methods

    public static boolean checkCPF(String cpf) {
        if (cpf.length() == 11) {
            for (int i = 0; i < cpf.length(); i++) {
                if (!Character.isDigit(cpf.charAt(i))) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    private void cadastrarDados(String nome, String cpf, Date dataDeNascimento, Endereco endereco, String email, String telefone) {
        cliente.setNome(nome);
        cliente.setCpf(cpf);
        cliente.setNome(nome);
        cliente.setDataDeNascimento(dataDeNascimento);
        cliente.setEndereco(endereco);
        cliente.setEmail(email);
        cliente.setTelefone(telefone);
    }

   // create with cartoes and pix
    public boolean createCliente(Cliente cliente){
        if(cliente != null){
              // Cria cartao de credito estatico
            CartaoCredito cartaoCredito = new CartaoCredito("visa","1234",new Date());

              // Cria cartao de debito estatico
            CartaoDebito cartaoDebito = new CartaoDebito("mastercard","1234",1500.0);

//            // Cria conta ao criar cliente
            Conta conta = new Conta(0.0,"1234",cliente,TipoConta.CORRENTE,cartaoCredito,cartaoDebito,0.0,new Date());

//            // Cria pix ao criar cliente
            Pix pix = new Pix();
            pix.ativarChave(TipoChavePix.CPF, conta.getCliente().getCpf());
            pix.setConta(conta);

            pixRepository.save(pix);

            return true;
        }
        return false;
    }

    // Delete
    public boolean deleteClienteById(Long id){
        Optional<Cliente> clienteToDelete = repository.findById(id);
        if(clienteToDelete.isPresent()){
            repository.delete(clienteToDelete.get());
            return true;
        }
        return false;
    }

}
