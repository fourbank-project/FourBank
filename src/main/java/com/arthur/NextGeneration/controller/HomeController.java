package com.arthur.NextGeneration.controller;

import com.arthur.NextGeneration.model.entities.*;
import com.arthur.NextGeneration.model.enums.TipoChavePix;
import com.arthur.NextGeneration.model.enums.TipoConta;
import com.arthur.NextGeneration.model.repositories.*;
import com.arthur.NextGeneration.model.services.ContaService;
import com.arthur.NextGeneration.model.services.PixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

@Controller
public class HomeController {

    @Autowired
    ContaRepository contaRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    EnderecoRepository enderecoRepository;

    @Autowired
    PixRepository pixRepository;

    @Autowired
    CartaoCreditoRepository cartaoCreditoRepository;

    @Autowired
    CompraRepository compraRepository;

    @Autowired
    SeguroRepository seguroRepository;

    @Autowired
    ApoliceRepository apoliceRepository;

    Conta contaLogada;

    @GetMapping(value = "/")
    public String getHome(){
        contaLogada = null;
        return "home";
    }

    @GetMapping(value = "/cadastro")
    public String getCadastrar(ModelMap model){
        try {
            contaLogada = null;
            Conta conta = new Conta();
            Cliente cliente = new Cliente();
            Endereco endereco = new Endereco();
            model.addAttribute("conta", conta);
            model.addAttribute("cliente", cliente);
            model.addAttribute("endereco", endereco);
            return "cadastro";
        } catch (Exception e) {
            return "/erro";
        }
    }

    @PostMapping(value = "/cadastrar")
    public String insertDados(Conta conta, Cliente cliente, Endereco endereco){

        try {
            cliente.setEndereco(endereco);
            conta.setCliente(cliente);

            if(conta.isCorrenteBool() && conta.isPoupancaBool()){
                Conta conta2 = conta.clone();
                conta.setTipoConta(TipoConta.CORRENTE);
                conta.setTaxa(0.0045);
                conta2.setTipoConta(TipoConta.POUPANCA);
                conta2.setTaxa(0.0003);
                enderecoRepository.saveAll(Arrays.asList(endereco));
                clienteRepository.saveAll(Arrays.asList(cliente));
                contaRepository.saveAll(Arrays.asList(conta,conta2));
                System.out.println("Eh nois");
                return "home";
            }
            else if(conta.isCorrenteBool()){
                conta.setTipoConta(TipoConta.CORRENTE);
                conta.setTaxa(0.0045);
            }else if(conta.isPoupancaBool()){
                conta.setTipoConta(TipoConta.POUPANCA);
                conta.setTaxa(0.0003);
            }else{
                System.out.println("Nenhuma Selecionada");
                return "cadastro";
            }
            enderecoRepository.saveAll(Arrays.asList(endereco));
            clienteRepository.saveAll(Arrays.asList(cliente));
            contaRepository.saveAll(Arrays.asList(conta));
            System.out.println("Eh nois");
            return "home";
        } catch (Exception e) {
            return "/erro";
        }
    }


    @GetMapping(value = "/login")
    public String getLogar(Model model){
        try {
            contaLogada = null;
            String login = new String();
            String senha = new String();
            Boolean corrente = false;
            Boolean poupanca = false;
            model.addAttribute("login", login);
            model.addAttribute("senha", senha);
            model.addAttribute("corrente", corrente);
            model.addAttribute("poupanca", poupanca);
            return "login";
        } catch (Exception e) {
            return "/erro";
        }
    }

    @PostMapping(value = "/logar")
    public String login(String login, String senha, Boolean corrente, Boolean poupanca){
        try {
            ArrayList<Conta> list = contaRepository.findAllByClienteCpf(login);
            if(list.isEmpty() || list == null){
                System.out.println("Não achou nada...");
                return "login";
            }
            if(corrente == null){
                corrente = false;
            }
            if(poupanca == null){
                poupanca = false;
            }
            Conta conta = new Conta();
            for(Conta contaVer: list){
                if(contaVer.getTipoConta().equals(TipoConta.CORRENTE) && corrente == true){
                    System.out.println("Achei sua conta!");
                    conta = contaVer;
                    break;
                }else if(contaVer.getTipoConta().equals(TipoConta.POUPANCA) && poupanca == true){
                    System.out.println("Achei sua conta!");
                    conta = contaVer;
                    break;
                }
            }
            if(conta == null){
                System.out.println("Dados Incorretos!");
                return "login";
            }else{
                if(conta.getSenha().equals(senha)){
                    System.out.println("Sucesso!");
                    contaLogada = conta;
                    return "redirect:/menu";
                }else{
                    System.out.println("Dados Incorretos!");
                    return "login";
                }
            }
        } catch (Exception e) {
            return "/erro";
        }
    }

    @GetMapping(value = "/menu")
    public String getLogado(ModelMap model){
        try {
            if(contaLogada == null){
                return "redirect:/";
            }
            String cpfExterno = new String();

            model.addAttribute("contaLogado", contaLogada);
            model.addAttribute("cpfExterno",cpfExterno);
            return "iniciar";
        } catch (Exception e) {
            return "/erro";
        }
    }

    // ENTRE RENDERIZAR A PÁGINA E LOGIN
    // MENU INICIAL NÃO POSSUI @GETMAPPING NEM @POSTMAPPING E FUNCIONOU

    // Mapeando TRANSAÇÕES

    @GetMapping(value = "/transacoes")
    public String getTransacoes(ModelMap model){
        try {
            String valorDeposito = new String();
            String cpfExterno = new String();
            Boolean corrente = false;
            Boolean poupanca = false;

            model.addAttribute("valorDeposito", valorDeposito);
            model.addAttribute("corrente", corrente);
            model.addAttribute("poupanca", poupanca);
            model.addAttribute("contaLogadinha", contaLogada);
            model.addAttribute("cpfExterno",cpfExterno);
            if(contaLogada == null){
                return "redirect:/";
            }
            return "transacoes";
        } catch (Exception e) {
            return "/erro";
        }
    }

    @PostMapping (value = "/godepositar")
    public String godepositar(Model model){
        try {
            model.addAttribute("contasuprema",contaLogada);
            return "depositar";
        } catch (Exception e) {
            return "/erro";
        }
    }

    @PostMapping (value = "/gosacar")
    public String gosacar(Model model){
        try {
            model.addAttribute("contasuprema",contaLogada);
            return "saque";
        } catch (Exception e) {
            return "/erro";
        }
    }

    @PostMapping (value = "/gotransferir")
    public String gotransferir(Model model){
        try {
            model.addAttribute("contasuprema", contaLogada);
            return "transferir";
        } catch (Exception e) {
            return "/erro";
        }
    }

    @GetMapping(value = "/depositare")
    public String getDeposito(Conta contaLogadinha){
        try{
            if(contaLogada == null){
                return "redirect:/";
            }
            System.out.println(contaLogadinha);
            return "depositar";
        } catch (Exception e) {
            return "/erro";
        }
    }

    @PostMapping (value = "/depositar")
    public String depositar(String valorDeposito, String login){
        try {
            contaLogada.setSaldo(contaLogada.getSaldo() + Double.parseDouble(valorDeposito));
            System.out.println(contaLogada.getSaldo());
            contaRepository.save(contaLogada);
            return "redirect:/menu";
        } catch (Exception e) {
            return "/erro";
        }
    }

    @GetMapping(value = "/menu/transacoes/sacar/")
    public String getSaque(){
        try {
            if(contaLogada == null){
                return "redirect:/";
            }
            return "saque";
        } catch (Exception e) {
            return "/erro";
        }
    }

    @PostMapping(value = "/sacar")
    public String postSacar(String valorDeposito){
        try {
            contaLogada.setSaldo(contaLogada.getSaldo() - Double.parseDouble(valorDeposito));
            System.out.println(contaLogada.getSaldo());
            contaRepository.save(contaLogada);
            return "redirect:/menu";
        } catch (Exception e) {
            return "/erro";
        }
    }

    @GetMapping(value = "/menu/transacoes/transferir/")
    public String getTransferencia(ModelMap model){
        try {
            if(contaLogada == null){
                return "redirect:/";
            }
            return "transferir"; // Página HTML
        } catch (Exception e) {
            return "/erro";
        }
    }

    @PostMapping(value = "/transferir")
    public String postTransferir(String valorDeposito, String cpfExterno, Boolean corrente, Boolean poupanca){
        try {
            ArrayList<Conta> list = contaRepository.findAllByClienteCpf(cpfExterno);
            if(list.isEmpty() || list == null){
                System.out.println("Não achou nada...");
                return "redirect:/menu/transacoes/transferir/";
            }
            if(corrente == null){
                corrente = false;
            }
            if(poupanca == null){
                poupanca = false;
            }
            Conta conta = new Conta();
            for(Conta contaVer: list){
                if(contaVer.getTipoConta().equals(TipoConta.CORRENTE) && corrente == true){
                    System.out.println("Achei sua conta!");
                    conta = contaVer;
                    break;
                }else if(contaVer.getTipoConta().equals(TipoConta.POUPANCA) && poupanca == true){
                    System.out.println("Achei sua conta!");
                    conta = contaVer;
                    break;
                }
            }
            if(conta == null){
                System.out.println("Dados Incorretos!");
                return "redirect:/menu/transacoes/transferir/";
            }
            ContaService contaProcessos = new ContaService(contaLogada);
            System.out.println(valorDeposito);
            if(!contaProcessos.transferir(Double.parseDouble(valorDeposito), conta)){
                System.out.println("Saldo Insuficiente");
                return "redirect:/menu/transacoes/transferir/";
            }
            contaRepository.save(contaLogada);
            contaRepository.save(conta);
            return "redirect:/menu";
        } catch (Exception e) {
            return "/erro";
        }
    }




    // -------------------------- É HORA DO PIX -------------------------------------


    @GetMapping(value = "/menu/pix/")
    public String pix(){
        try{
            if(contaLogada == null){
                return "redirect:/";
            }
            return "pix";
        } catch (Exception e) {
            return "/erro";
        }
    }


    @GetMapping(value = "/menu/pix/cadastrarpix/")
    public String getCadastroPix(){
        try{
            if(contaLogada == null){
                return "redirect:/";
            }
            return "cadastrapix";
        } catch (Exception e) {
            return "/erro";
        }
    }

    @PostMapping(value = "/gocadastrarpix")
    public String goCadastrarPix(ModelMap model){
        try{
            if(contaLogada == null){
                return "redirect:/";
            }
            Boolean cpf = false;
            Boolean email = false;
            Boolean telefone = false;
            Boolean aleatorio = false;

            model.addAttribute("valor","");
            model.addAttribute("cpf",cpf);
            model.addAttribute("email",email);
            model.addAttribute("telefone",telefone);
            model.addAttribute("aleatorio",aleatorio);
            return "cadastrapix";

        } catch (Exception e) {
            return "/erro";
        }
    }

    @PostMapping(value = "/btcadastrarpix")
    public String btCadastrarPix(String valor, Boolean cpf, Boolean email, Boolean telefone, Boolean aleatorio){
        try {
            if(contaLogada == null){
                return "redirect:/";
            }
            if (cpf == null) {
                cpf = false;
            }
            if (email == null) {
                email = false;
            }
            if (telefone == null) {
                telefone = false;
            }
            if (aleatorio == null) {
                aleatorio = false;
            }

            Pix aleatoria = null;
            ArrayList<Pix> list = pixRepository.findAllByConta(contaLogada);
            for (Pix pix : list) {
                if (pix.getChavePix().equals(TipoChavePix.ALEATORIO)) {
                    aleatoria = pix;
                }
            }
            System.out.println(cpf);
            System.out.println(email);
            System.out.println(telefone);
            System.out.println(aleatorio);

            if (cpf) {
                System.out.println("Buscando CPF");
                Pix pixBuscado = pixRepository.findByConteudoChave(contaLogada.getCliente().getCpf());
                System.out.println("Buscou CPF");
                if (pixBuscado == null) {
                    System.out.println("Não Existe");
                    Pix pix = new Pix(TipoChavePix.CPF, Double.valueOf(valor), contaLogada.getCliente().getCpf(), true, contaLogada);
                    pixRepository.save(pix);
                    System.out.println("Salvou um novo pix - CPF");
                } else {
                    System.out.println("Existia");
                    pixBuscado.setValor(Double.valueOf(valor));
                    pixRepository.save(pixBuscado);
                    System.out.println("Update pix - CPF");
                }
            }
            if (email) {
                System.out.println("Buscando Email");
                Pix pixBuscado = pixRepository.findByConteudoChave(contaLogada.getCliente().getEmail());
                System.out.println("Buscou Email");
                if (pixBuscado == null) {
                    System.out.println("Não Existe");
                    Pix pix = new Pix(TipoChavePix.EMAIL, Double.valueOf(valor), contaLogada.getCliente().getEmail(), true, contaLogada);
                    pixRepository.save(pix);
                    System.out.println("Salvou um novo pix - Email");
                } else {
                    System.out.println("Existia");
                    pixBuscado.setValor(Double.valueOf(valor));
                    pixRepository.save(pixBuscado);
                    System.out.println("Update pix - Email");
                }
            }
            if (telefone) {
                System.out.println("Buscando Telefone");
                Pix pixBuscado = pixRepository.findByConteudoChave(contaLogada.getCliente().getTelefone());
                System.out.println("Buscou Telefone");
                if (pixBuscado == null) {
                    System.out.println("Não Existia");
                    Pix pix = new Pix(TipoChavePix.TELEFONE, Double.valueOf(valor), contaLogada.getCliente().getTelefone(), true, contaLogada);
                    pixRepository.save(pix);
                    System.out.println("Salvou um novo pix - Telefone");
                } else {
                    System.out.println("Existia");
                    pixBuscado.setValor(Double.valueOf(valor));
                    pixRepository.save(pixBuscado);
                    System.out.println("Update pix - Telefone");
                }
            }
            if (aleatorio) {
                if (aleatoria == null) {
                    System.out.println("Não Existia");
                    PixService pixService = new PixService();
                    Pix pix = new Pix(TipoChavePix.ALEATORIO, Double.valueOf(valor), pixService.gerarRandomNumber(), true, contaLogada);
                    pixRepository.save(pix);
                    System.out.println("Salvou um novo pix - Aleatorio");
                } else {
                    System.out.println("Existia");
                    PixService pixService = new PixService();
                    aleatoria.setConteudoChave(pixService.gerarRandomNumber());
                    aleatoria.setValor(Double.valueOf(valor));
                    pixRepository.save(aleatoria);
                    System.out.println("Update pix - Aleatorio");
                }
            }
            return "pix";

        } catch (Exception e) {
            return "/erro";
        }
    }

    @PostMapping(value = "/goconsultarpix")
    public String goConsultarPix(ModelMap model){
        try{
            if(contaLogada == null){
                return "redirect:/";
            }
            ArrayList<Pix> list = pixRepository.findAllByConta(contaLogada);
            String tipoCPF = "Não cadastrada";
            String valorCPF = "Não cadastrado";
            String tipoEmail = "Não cadastrada";
            String valorEmail = "Não cadastrado";
            String tipoTelefone = "Não cadastrada";
            String valorTelefone = "Não cadastrado";
            String tipoAleatorio = "Não cadastrada";
            String valorAleatorio = "Não cadastrado";
            for(Pix pix: list){
                if(pix.getChavePix().equals(TipoChavePix.CPF)){
                    tipoCPF = pix.getConteudoChave();
                    valorCPF = String.valueOf(pix.getValor());
                }else if(pix.getChavePix().equals(TipoChavePix.EMAIL)){
                    tipoEmail = pix.getConteudoChave();
                    valorEmail = String.valueOf(pix.getValor());
                }else if(pix.getChavePix().equals(TipoChavePix.TELEFONE)){
                    tipoTelefone = pix.getConteudoChave();
                    valorTelefone = String.valueOf(pix.getValor());
                }else if(pix.getChavePix().equals(TipoChavePix.ALEATORIO)){
                    tipoAleatorio = pix.getConteudoChave();
                    valorAleatorio = String.valueOf(pix.getValor());
                }
            }
            model.addAttribute("contasuprema",contaLogada);
            model.addAttribute("tipoCPF", tipoCPF);
            model.addAttribute("valorCPF", valorCPF);
            model.addAttribute("tipoEmail", tipoEmail);
            model.addAttribute("valorEmail", valorEmail);
            model.addAttribute("tipoTelefone", tipoTelefone);
            model.addAttribute("valorTelefone", valorTelefone);
            model.addAttribute("tipoAleatorio", tipoAleatorio);
            model.addAttribute("valorAleatorio", valorAleatorio);
            return "consultachave";

        } catch (Exception e) {
            return "/erro";
        }
    }

    @PostMapping(value = "/gotransferirpix")
    public String goTransferirPix(ModelMap model){
        try{
            if(contaLogada == null){
                return "redirect:/";
            }
            model.addAttribute("chave","");
            return "tranferirpix";

        } catch (Exception e) {
            return "/erro";
        }
    }

    @PostMapping(value = "/bttransferirpix")
    public String btTransferirPix(String chave){
        try{
            if(contaLogada == null){
                return "redirect:/";
            }
            Pix pix = pixRepository.findByConteudoChave(chave);
            Conta contaPagamento = pix.getConta();
            ContaService cs = new ContaService(contaLogada);
            ContaService cs2 = new ContaService(contaPagamento);
            if(!cs.sacar(pix.getValor())){
                throw new Exception("Saldo Insuficiente");
            }
            cs2.depositar(pix.getValor());
            contaRepository.saveAll(Arrays.asList(contaLogada,contaPagamento));

            return "pix";

        } catch (Exception e) {
            return "/erro";
        }
    }

    @GetMapping(value = "/menu/pix/consultarchave/")
    public String getConsultarPix(ModelMap model){
        try{
            if(contaLogada == null){
                return "redirect:/";
            }
            model.addAttribute("contasuprema",contaLogada);

            return "consultachave";

        } catch (Exception e) {
            return "/erro";
        }
    }

    @GetMapping(value = "/menu/pix/transferir/")
    public String getTransferirPix(){
        try{
            if(contaLogada == null){
                return "redirect:/";
            }
            return "transferirpix";

        } catch (Exception e) {
            return "/erro";
        }
    }



    // ---------------------------------- VEM DE CARTÃO -------------------------------


    @GetMapping(value = "/cartoes")
    public String cartoes(){
        try{
            if(contaLogada == null){
                return "redirect:/";
            }

            return "cartoes";

        } catch (Exception e) {
            return "/erro";
        }
    }

    // --------------------------------------- Credito -----------------------------------

    @GetMapping(value = "/credito")
    public String creditoPage(ModelMap model){
        try{
            if(contaLogada == null){
                return "redirect:/";
            }
            String statusCartao = "";
            String iconeCadeado = "";
            String fatura = "";
            String seguros = "";
            String compras = "";
            if(contaLogada.getCartaoCredito() == null){
                compras = "Opção Indisponível";
                statusCartao = "Opção Indisponível";
                fatura = "Opção Indisponível";
                seguros = "Opção Indisponível";
                iconeCadeado = "\uE95F";
            }else if(contaLogada.getCartaoCredito().isAtivo()){
                compras = "Fazer Compras";
                statusCartao = "Bloquear Cartão";
                seguros = "Seguros";
                fatura = "Pagar Fatura: R$ "+contaLogada.getCartaoCredito().getValorFatura();
                iconeCadeado = "\uE951";
            }else{
                compras = "Compras Bloqueadas";
                seguros = "Seguros Bloqueados";
                statusCartao = "Desbloquear Cartão";
                fatura = "Pagamento Bloqueado";
                iconeCadeado = "\uE950";
            }

            model.addAttribute("compras",compras);
            model.addAttribute("seguros",seguros);
            model.addAttribute("fatura", fatura);
            model.addAttribute("logo",iconeCadeado);
            model.addAttribute("statuscartao",statusCartao);
            return "credito";

        } catch (Exception e) {
            return "/erro";
        }
    }

    @PostMapping(value = "/goback")
    public String goBack(){
        try{
            if(contaLogada == null){
                return "redirect:/";
            }
            if(!contaLogada.getCartaoCredito().isAtivo()){
                return "redirect:/credito";
            }
            if(!contaLogada.getCartaoCredito().pagarFatura(contaLogada)){
                throw new Exception("Saldo Insuficiente...");
            }

            return "redirect:/menu";

        } catch (Exception e) {
            return "/erro";
        }
    }

    @PostMapping(value = "/refreshsaldo")
    public String refreshSaldo(){
        try{
            if(contaLogada == null){
                return "redirect:/";
            }

            return "redirect:/credito";

        } catch (Exception e) {
            return "/erro";
        }
    }

    // Transição Crédito e Solicitar Cartão

    @PostMapping(value = "/gosolicitarcartaocredito")
    public String goSolicitarCartaoCredito(ModelMap model){
        try{
            if(contaLogada == null) {
                return "redirect:/";
            }
            if(contaLogada.getCartaoCredito() != null){
                return "redirect:/credito";
            }
            CartaoCredito cartaoCredito = new CartaoCredito();
            model.addAttribute("cartao",cartaoCredito);

            return "solicitarcartaocredito";

        } catch (Exception e) {
            return "/erro";
        }
    }

    @PostMapping(value = "/btsolicitarcartaocredito")
    public String btSolicitarCartaoCredito(CartaoCredito cartao){
        try{
            if(contaLogada == null) {
                return "redirect:/";
            }
            cartao.setAtivo(true);
            cartaoCreditoRepository.save(cartao);
            contaLogada.setCartaoCredito(cartao);
            contaRepository.save(contaLogada);

            return "redirect:/credito";

        } catch (Exception e) {
            return "/erro";
        }
    }


    // Transição Crédito e Bloquear Cartão

    @PostMapping(value = "/btbloquearcartaocredito")
    public String btBloquearCartaoCredito(){
        try{
            if(contaLogada == null) {
                return "redirect:/";
            }
            if(contaLogada.getCartaoCredito() == null){
            }else if(contaLogada.getCartaoCredito().isAtivo()){
                contaLogada.getCartaoCredito().setAtivo(false);
            }else{
                contaLogada.getCartaoCredito().setAtivo(true);
            }
            cartaoCreditoRepository.save(contaLogada.getCartaoCredito());
            return "redirect:/credito";

        } catch (Exception e) {
            return "/erro";
        }
    }


    // FAZER COMPRAS NO CARTÃO DE CRÉDITO

    @PostMapping(value = "/gocomprarcartaocredito")
    public String goComprarCartaoCredito(ModelMap model){
        try{
            if(contaLogada == null) {
                return "redirect:/";
            }
            if(!contaLogada.getCartaoCredito().isAtivo() || contaLogada.getCartaoCredito() == null){
                return "redirect:/credito";
            }
            Compra compra = new Compra();
            model.addAttribute("compra",compra);

            return "compras";

        } catch (Exception e) {
            return "/erro";
        }
    }

    @PostMapping(value = "/btcomprarcartaocredito")
    public String btComprarCartaoCredito(Compra compra){
        try{
            if(contaLogada == null) {
                return "redirect:/";
            }

            compraRepository.save(compra);
            contaLogada.getCartaoCredito().addCompra(compra);
            cartaoCreditoRepository.save(contaLogada.getCartaoCredito());
            return "redirect:/credito";

        } catch (Exception e) {
            return "/erro";
        }
    }


    //


    @PostMapping(value = "/btpagarfaturacartaocredito")
    public String btPagarFaturaCartaoCredito(){
        try{
            if(contaLogada == null) {
                return "redirect:/";
            }
            if(!contaLogada.getCartaoCredito().isAtivo() || contaLogada.getCartaoCredito() == null){
                return "redirect:/credito";
            }
            return "redirect:/credito";

        } catch (Exception e) {
            return "/erro";
        }
    }


    // ----------------------------------- Debito ---------------------------------------

    @PostMapping(value = "/godebito")
    public String goDebito(ModelMap model){
        try{
            if(contaLogada == null){
                return "redirect:/";
            }
            model.addAttribute("conta",contaLogada);
            return "debito";

        } catch (Exception e) {
            return "/erro";
        }
    }





    // ---------------------------------- Seguros -----------------------------------

    @PostMapping(value = "/goseguros")
    public String goSeguros(){
        try{
            if(contaLogada == null){
                return "redirect:/";
            }
            if(!contaLogada.getCartaoCredito().isAtivo() || contaLogada.getCartaoCredito() == null){
                return "redirect:/credito";
            }
            return "seguros";

        } catch (Exception e) {
            return "/erro";
        }
    }

    @GetMapping(value = "/seguros")
    public String seguros(){
        try{
            if(contaLogada == null){
                return "redirect:/";
            }
            if(!contaLogada.getCartaoCredito().isAtivo() || contaLogada.getCartaoCredito() == null){
                return "redirect:/credito";
            }
            return "seguros";

        } catch (Exception e) {
            return "/erro";
        }
    }


    @GetMapping(value = "/gosegurovida")
    public String goSeguroVida(ModelMap model){
        try{
            if(contaLogada == null){
                return "redirect:/";
            }

            String contratado = "Contratar";
            Seguro seguro = seguroRepository.findById(2L).orElse(null);
            if(seguro == null){
                System.out.println("Seguro Não Existe...");
                throw new Exception("Seguro Não Existe...");
            }
            for(Apolice apolice : contaLogada.getCartaoCredito().getApolices()){
                if(apolice.getSeguro().equals(seguro)){
                    contratado = "Resgatar";
                }
            }
            model.addAttribute("contratado", contratado);
            model.addAttribute("conta",contaLogada);

            return "segurodevida";

        } catch (Exception e) {
            return "/erro";
        }
    }

    @GetMapping(value = "/gosegurodesemprego")
    public String goSeguroDesemprego(ModelMap model){
        try{
            if(contaLogada == null){
                return "redirect:/";
            }

            String contratado = "Contratar";
            Seguro seguro = seguroRepository.findById(4L).orElse(null);
            if(seguro == null){
                System.out.println("Seguro Não Existe...");
                throw new Exception("Seguro Não Existe...");
            }
            for(Apolice apolice : contaLogada.getCartaoCredito().getApolices()){
                if(apolice.getSeguro().equals(seguro)){
                    contratado = "Resgatar";
                }
            }
            model.addAttribute("contratado", contratado);
            model.addAttribute("conta",contaLogada);

            return "segurodesemprego";

        } catch (Exception e) {
            return "/erro";
        }
    }

    @GetMapping(value = "/goseguroinvalidez")
    public String goSeguroInvalidez(ModelMap model){
        try{
            if(contaLogada == null){
                return "redirect:/";
            }
            String contratado = "Contratar";
            Seguro seguro = seguroRepository.findById(3L).orElse(null);
            if(seguro == null){
                System.out.println("Seguro Não Existe...");
                throw new Exception("Seguro Não Existe...");
            }
            for(Apolice apolice : contaLogada.getCartaoCredito().getApolices()){
                if(apolice.getSeguro().equals(seguro)){
                    contratado = "Resgatar";
                }
            }
            model.addAttribute("contratado", contratado);
            model.addAttribute("conta",contaLogada);

            return "seguroinvalidez";

        } catch (Exception e) {
            return "/erro";
        }
    }

    @PostMapping(value = "/contratarinvalidez")
    public String contratarInvalidez(){
        try{
            if(contaLogada == null){
                return "redirect:/";
            }

            Seguro seguro = seguroRepository.findById(3L).orElse(null);
            if(seguro == null){
                System.out.println("Seguro Não Existe...");
                throw new Exception("Seguro Não Existe...");
            }
            ContaService cs = new ContaService(contaLogada);
            for(Apolice apolice : contaLogada.getCartaoCredito().getApolices()){
                if(apolice.getSeguro().equals(seguro)){
                    cs.depositar(apolice.getValorApolice());
                    contaLogada.getCartaoCredito().removeApolice(apolice);
                    cartaoCreditoRepository.save(contaLogada.getCartaoCredito());
                    contaRepository.save(contaLogada);
                    return "redirect:/seguros";
                }
            }
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_YEAR,15 );
                Apolice apolice = new Apolice(
                        seguro.getValorAno(),
                        seguro.getRegras(),
                        seguro,
                        Calendar.getInstance().getTime(),
                        calendar.getTime());


                if(!cs.sacar(seguro.getValorAno())){
                    throw new Exception("Saldo Insuficiente");
                }

                apoliceRepository.save(apolice);
                contaLogada.getCartaoCredito().addApolice(apolice);
                cartaoCreditoRepository.save(contaLogada.getCartaoCredito());
                contaRepository.save(contaLogada);
                return "redirect:/seguros";

        } catch (Exception e) {
            return "/erro";
        }
    }

    @PostMapping(value = "/contratarvida")
    public String contratarVida(){
        return null;
    }

    @PostMapping(value = "/contratardesemprego")
    public String contratarDesemprego(){
        return null;
    }



}
