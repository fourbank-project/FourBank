package com.arthur.NextGeneration.model.services;

import com.arthur.NextGeneration.model.entities.Conta;
import com.arthur.NextGeneration.model.entities.Cliente;
import com.arthur.NextGeneration.model.entities.Conta;
import com.arthur.NextGeneration.model.entities.Recarga;
import com.arthur.NextGeneration.model.enums.TipoCliente;
import com.arthur.NextGeneration.model.enums.TipoConta;
import com.arthur.NextGeneration.model.repositories.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ContaService {

    @Autowired
    private ContaRepository repository;

    private Conta conta = new Conta();

    public ContaService(){}

    public ContaService(Conta conta){
        this.conta = conta;
    }

    public ContaService(String senha, Cliente cliente, TipoConta tipoConta) {
        cadastrarConta(senha,cliente,tipoConta);
        Calendar calendarAplicacaoTaxa = Calendar.getInstance();
        calendarAplicacaoTaxa.add(Calendar.MONTH, 1);
        Date dataAplicacaoTaxa = calendarAplicacaoTaxa.getTime();
        conta.setDataTaxa(dataAplicacaoTaxa);
    }

    // Search Methods
    public List<Conta> findAll() {
        return repository.findAll();
    }


    public Conta findById(Long id) {
        Optional<Conta> optional = repository.findById(id);
        return optional.get();
    }

    // Create/Update
    public boolean createConta(Conta conta){
        if(conta != null){
            repository.save(conta);
            return true;
        }
        return false;
    }

    // Create/Update
    public boolean createConta(){
        if(conta != null){
            repository.save(conta);
            return true;
        }
        return false;
    }

    // Delete
    public boolean deleteContaById(Long id){
        Optional<Conta> contaToDelete = repository.findById(id);
        if(contaToDelete.isPresent()){
            repository.delete(contaToDelete.get());
            return true;
        }
        return false;
    }

    // Validation Methods

    public Conta findBySenhaAndEmail(String senha,String email) {
        return repository.findBySenhaAndClienteEmail(senha,email);
    }

    public boolean transferir(double valor, Conta externa){
        if(conta.getTipoConta().equals(externa.getTipoConta())){
            if(conta.getSaldo() < valor){
                return false;
            }else{
                externa.setSaldo(externa.getSaldo() + valor);
                conta.setSaldo(conta.getSaldo()- valor);

                if (externa.getSaldo() < 5000){
                    externa.getCliente().setTipo(TipoCliente.COMUM);
                } else if(externa.getSaldo() < 15000){
                    externa.getCliente().setTipo(TipoCliente.SUPER);
                }else{
                    externa.getCliente().setTipo(TipoCliente.PREMIUM);
                }

                if (conta.getSaldo() < 5000){
                    conta.getCliente().setTipo(TipoCliente.COMUM);
                } else if(conta.getSaldo() < 15000){
                    conta.getCliente().setTipo(TipoCliente.SUPER);
                }else{
                    conta.getCliente().setTipo(TipoCliente.PREMIUM);
                }
                return true;
            }
        }else{
            if(conta.getSaldo() < (valor + 5.6)) {
                return false;
            }else{
                externa.setSaldo(externa.getSaldo() + valor);
                conta.setSaldo(conta.getSaldo()-(valor + 5.6));

                if (externa.getSaldo() < 5000){
                    externa.getCliente().setTipo(TipoCliente.COMUM);
                } else if(externa.getSaldo() < 15000){
                    externa.getCliente().setTipo(TipoCliente.SUPER);
                }else{
                    externa.getCliente().setTipo(TipoCliente.PREMIUM);
                }

                if (conta.getSaldo() < 5000){
                    conta.getCliente().setTipo(TipoCliente.COMUM);
                } else if(conta.getSaldo() < 15000){
                    conta.getCliente().setTipo(TipoCliente.SUPER);
                }else{
                    conta.getCliente().setTipo(TipoCliente.PREMIUM);
                }
                return true;
            }
        }
    }

    public void depositar(double valor){
        conta.setSaldo(conta.getSaldo()+valor);
        if (conta.getSaldo() < 5000){
            conta.getCliente().setTipo(TipoCliente.COMUM);
        } else if(conta.getSaldo() < 15000){
            conta.getCliente().setTipo(TipoCliente.SUPER);
        }else{
            conta.getCliente().setTipo(TipoCliente.PREMIUM);
        }
    }

    private void cadastrarConta(String senha, Cliente cliente, TipoConta tipoConta){
        conta.setSaldo(0.0);
        conta.setSenha(senha);
        conta.setCliente(cliente);
        conta.setTipoConta(tipoConta);

        if(tipoConta.equals(TipoConta.CORRENTE)){
            conta.setTaxa(0.0045);
        }else{
            conta.setTaxa(0.0003);
        }
    }

    public boolean sacar(double valor){
        if(conta.getSaldo() < valor){
            return false;
        }
        this.conta.setSaldo(conta.getSaldo() - valor);
        return true;
    }

    public static boolean checkSenhaValida(String senha){
        return !senha.contains(" ") && (senha.length() >= 6 && senha.length() <= 16);
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public void aplicaTaxa(){
        if(conta.getTipoConta() != null){
            if(conta.getDataTaxa().before(new Date())){
                if(conta.getTipoConta().equals(TipoConta.CORRENTE)){
                    conta.setSaldo(conta.getSaldo() - (conta.getSaldo() * conta.getTaxa()));
                }else{
                    conta.setSaldo(conta.getSaldo() + (conta.getSaldo() * conta.getTaxa()));
                }
                Calendar calendarAplicacaoTaxa = Calendar.getInstance();
                calendarAplicacaoTaxa.add(Calendar.MONTH, 1);
                Date dataAplicacaoTaxa = calendarAplicacaoTaxa.getTime();
                conta.setDataTaxa(dataAplicacaoTaxa);
            }
        }
    }

    @Override
    public String toString() {
        return "Nome: " + conta.getCliente().getNome()+
                " CPF: " + conta.getCliente().getCpf() +
                " Conta: " + conta.getTipoConta().name();
    }

    public void saveAll(List<Conta> asList) {
    }


}
