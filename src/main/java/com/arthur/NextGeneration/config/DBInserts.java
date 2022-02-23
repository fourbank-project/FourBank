package com.arthur.NextGeneration.config;

import com.arthur.NextGeneration.model.entities.*;
import com.arthur.NextGeneration.model.enums.TipoChavePix;
import com.arthur.NextGeneration.model.enums.TipoConta;
import com.arthur.NextGeneration.model.repositories.*;
import com.arthur.NextGeneration.model.services.ClienteService;
import com.arthur.NextGeneration.model.services.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

@Configuration
@Profile("db")
public class DBInserts implements CommandLineRunner {

    @Autowired
    private ApoliceRepository apoliceRepository;

    @Autowired
    private CartaoCreditoRepository cartaoCreditoRepository;

    @Autowired
    private CartaoDebitoRepository cartaoDebitoRepository;

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private PixRepository pixRepository;

    @Autowired
    private SeguroRepository seguroRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private RecargaRepository recargaRepository;

    @Override
    public void run(String... args) throws Exception {
        Endereco endereco1 = new Endereco(
                null,
                "Rua 1",
                "Centro",
                "Rio de Janeiro",
                "RJ",
                "123",
                "12345-678");

        Endereco endereco2 = new Endereco(
                null,
                "Rua 2",
                "Centro",
                "Rio de Janeiro",
                "RJ",
                "124",
                "12345-679");

        Date hoje = Calendar.getInstance().getTime();

        Cliente cliente1 = new ClienteService(
                "123.456.789-10",
                "Arthur",
                hoje,
                endereco1,
                "arthur@gmail.com",
                "+55(21)99999-9999").getCliente();

        Cliente cliente2 = new ClienteService(
                "123.456.789-11",
                "Kimberly",
                hoje,
                endereco2,
                "Kimberly@gmail.com",
                "+55(21)99999-9999").getCliente();

        Conta conta1 = new ContaService("1234567", cliente1, TipoConta.CORRENTE).getConta();
        Conta conta2 = new ContaService("KimberlyS2Arthur", cliente2, TipoConta.POUPANCA).getConta();

        CartaoCredito cc = new CartaoCredito("Visa", "123456", hoje);
        CartaoCredito cc2 = new CartaoCredito("MasterCard", "123456", hoje);

        CartaoDebito cd = new CartaoDebito("MasterCard", "123456", 1000.0);
        CartaoDebito cd2 = new CartaoDebito("Visa", "123456", 1000.0);

        conta1.setCartaoCredito(cc);
        conta1.setCartaoDebito(cd);
        conta2.setCartaoCredito(cc2);
        conta2.setCartaoDebito(cd2);

        Seguro seguro = new Seguro(null, "Seguro vida", "Condição 1" , 100.0);
        Apolice ap = new Apolice(100.0, "Condição 1", seguro, hoje, hoje);

        conta1.getCartaoCredito().addApolice(ap);
        conta2.getCartaoCredito().addApolice(ap);

        Pix pix = new Pix(TipoChavePix.CPF, 100.0, cliente1.getCpf(), true, conta1);
        Pix pix2 = new Pix(TipoChavePix.CPF, 100.0, cliente2.getCpf(), true, conta2);

//        if(contaRepository.count() == 0) {
//            seguroRepository.saveAll(Arrays.asList(seguro));
//            apoliceRepository.saveAll(Arrays.asList(ap));
//            cartaoCreditoRepository.saveAll(Arrays.asList(cc, cc2));
//            cartaoDebitoRepository.saveAll(Arrays.asList(cd, cd2));
//            enderecoRepository.saveAll(Arrays.asList(endereco1, endereco2));
//            clienteRepository.saveAll(Arrays.asList(cliente1, cliente2));
//            contaRepository.saveAll(Arrays.asList(conta1, conta2));
//            pixRepository.saveAll(Arrays.asList(pix, pix2));
//        }
//        if(seguroRepository.count() == 0) {
//            Seguro seguro1 = new Seguro(null,"Seguro de Vida", "Indenização por despesas médico-hospitalares, ou por perda parcial ou total do funcionamento dos membros ou órgãos;",36.00);
//            Seguro seguro2 = new Seguro(null,"Seguro Invalidez", "Indenização por despesas médico-hospitalares, ou por perda parcial ou total do funcionamento dos membros ou órgãos;",36.00);
//            Seguro seguro3 = new Seguro(null,"Seguro Desemprego", "Indenização por despesas médico-hospitalares, ou por perda parcial ou total do funcionamento dos membros ou órgãos;",36.00);
//            seguroRepository.saveAll(Arrays.asList(seguro1,seguro2,seguro3));
//        }
    }

    private void deleteAll(){
        pixRepository.deleteAll();
        contaRepository.deleteAll();
        clienteRepository.deleteAll();
        cartaoCreditoRepository.deleteAll();
        cartaoDebitoRepository.deleteAll();
        apoliceRepository.deleteAll();
        seguroRepository.deleteAll();
        enderecoRepository.deleteAll();
    }
}
