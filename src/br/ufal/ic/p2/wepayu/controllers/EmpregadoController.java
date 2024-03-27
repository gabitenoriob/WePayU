package br.ufal.ic.p2.wepayu.controllers;

import br.ufal.ic.p2.wepayu.exceptions.*;

import br.ufal.ic.p2.wepayu.models.empregado.*;
import br.ufal.ic.p2.wepayu.models.empregado.Sindicato;
import br.ufal.ic.p2.wepayu.utils.ConvertDoubleToString;
import br.ufal.ic.p2.wepayu.utils.Verification;

import java.text.DecimalFormat;
import java.util.*;


import static br.ufal.ic.p2.wepayu.controllers.ServicoController.sindicatos;
import static br.ufal.ic.p2.wepayu.controllers.SistemaController.empregados;
import static br.ufal.ic.p2.wepayu.controllers.SistemaController.empregadosPersistencia;
import static br.ufal.ic.p2.wepayu.utils.IsNumeric.isNumeric;


public class EmpregadoController {


    public static void removerEmpregado(String emp) throws IdentificacaoNula, EmpregadoNaoExisteException {
        if (!Verification.verificationEmpregado(emp))
            return;

        empregados.remove(emp);
    }

    //rever
    public static String getEmpregadoPorNome(String nome, int indice) throws NaoExisteNome {
        String idAchado;


        for (String key : empregados.keySet()) {

            if (empregados.get(key).getNome().equals(nome)) {
                indice--;
                if (indice == 0) {
                    idAchado = empregados.get(key).getId();
                    return idAchado;
                }

            }
        }
        throw new NaoExisteNome();

    }

    public static String criarEmpregado(String nome, String endereco, String tipo, String salario) throws Exception {

        if (tipo.equals("comissionado")) {
            throw new TipoNaoAplicavel();
        } else if (!(tipo.equals("horista")) && !(tipo.equals("assalariado"))) {
            throw new TipoInvalido();
        }

        if (Verification.verificationCriarEmpregado(nome, endereco, salario)) {
            String id = UUID.randomUUID().toString();
            double salarioDouble = Double.parseDouble(salario.replace(",", "."));

            Empregado novo = null;

            if (tipo.equals("horista")) {
                novo = new EmpregadoHorista(nome, endereco, salarioDouble);
            } else if (tipo.equals("assalariado")) {
                novo = new EmpregadoAssalariado(nome, endereco, salarioDouble);
            }

            empregados.put(id, novo);
            novo.setId(id);
            empregadosPersistencia.add(novo);
            return id;
        }

        return null;
    }


    public static String criarEmpregado(String nome, String endereco, String tipo, String salario, String comissao) throws Exception {

        if (tipo.equals("comissionado")) {
            if (comissao.isBlank()) {
                throw new ComissaoNula();
            }
            if (!isNumeric(comissao.replace(",", "."))) {
                throw new ComissaoNumerica();
            }
            if (comissao.contains("-")) {
                throw new ComissaoPositiva();
            }
        } else if (tipo.equals("horista") || tipo.equals("assalariado")) {
            throw new TipoNaoAplicavel();
        } else if (!(tipo.equals("horista")) && !(tipo.equals("assalariado")) && !(tipo.equals("comissionado"))) {
            throw new TipoInvalido();
        }

        if (Verification.verificationCriarEmpregado(nome, endereco, salario)) {
            double salarioDouble = Double.parseDouble(salario.replace(",", "."));
            double comissaoDouble = Double.parseDouble(comissao.replace(",", "."));
            Empregado novo = new EmpregadoComissionado(nome, endereco, salarioDouble, comissaoDouble);

            // ta certo System.out.println(novo.getNome() + " " + novo.getTipo()) ;
            String id = UUID.randomUUID().toString();
            empregados.put(id, novo);
            novo.setId(id);
            empregadosPersistencia.add(novo);
            return id;
        }

        return null;
    }


    public static String getAtributoEmpregado(String emp, String atributo) throws EmpregadoNaoExisteException, IdentificacaoNula, AtributoNaoExiste, NaoBanco, NaoSindicalizado, NaoComissionado {
        String resultado = null;

        if (!Verification.verificationEmpregado(emp))
            return resultado;

        Empregado buscado = empregados.get(emp);

        if (atributo.equals("nome")) {
            resultado = buscado.getNome();
        } else if (atributo.equals("endereco")) {
            resultado = buscado.getEndereco();
        } else if (atributo.equals("tipo")) {
            resultado = buscado.getTipo();
        } else if (atributo.equals("salario")) {
            resultado = ConvertDoubleToString.convertDoubleToString(buscado.getSalario(), 2);
            if (!(resultado.contains(",")) && !(resultado.contains("."))) {
                resultado = resultado + ",00";
            }

            return resultado;
        } else if (atributo.equals("sindicalizado")) {

            Sindicato sindicato = buscado.getSindicato();

            if (sindicato == null) {
                return "false";
            } else {
                return "true";
            }
        } else if (atributo.equals("comissao")) {
            // ok System.out.println("entra aq");

            if (buscado.getTipo().equals("comissionado")) {
                resultado = ConvertDoubleToString.convertDoubleToString(((EmpregadoComissionado) buscado).getComissao(), 2);
            } else {//TA VINDO P CA
                throw new NaoComissionado();
            }

        } else if (atributo.equals("metodoPagamento")) {
            MetodoPagamento metodoPagamento = buscado.getMetodoPagamento();
            return metodoPagamento.getMetodoPagamento();
        } else if (atributo.equals("banco")) {
            if (buscado.getMetodoPagamento().getMetodoPagamento().equals("banco")) {
                resultado = String.valueOf(((MetodoPagamentoBanco) buscado.getMetodoPagamento()).getBanco());
            } else {
                throw new NaoBanco();
            }
        } else if (atributo.equals("taxaSindical")) {
            if (buscado.getSindicato() != null) {
                double taxaSindical = buscado.getSindicato().getTaxaSindical();

                DecimalFormat df = new DecimalFormat("#0.00");
                String resultadoFormatado = df.format(taxaSindical);

                resultado = resultadoFormatado.replace(".", ",");
            } else {
                throw new NaoSindicalizado();
            }
        } else if (atributo.equals("idSindicato")) {
            if (buscado.getSindicato() != null) {
                resultado = buscado.getSindicato().getIdSindicato();
            } else {
                throw new NaoSindicalizado();
            }
        } else if (atributo.equals("agencia")) {
            if (buscado.getMetodoPagamento().getMetodoPagamento().equals("banco")) {
                resultado = String.valueOf(((MetodoPagamentoBanco) buscado.getMetodoPagamento()).getBanco());
            } else {
                throw new NaoBanco();
            }
        } else if (atributo.equals("contaCorrente")) {
            if (buscado.getMetodoPagamento().getMetodoPagamento().equals("banco")) {
                resultado = String.valueOf(((MetodoPagamentoBanco) buscado.getMetodoPagamento()).getBanco());
            } else {
                throw new NaoBanco();
            }
        } else if (atributo.equals("agendaPagamento")) {
            resultado = buscado.getAgendaPagamento();
        } else {
            throw new AtributoNaoExiste();
        }
        return resultado;
    }

    //altera empregados = faço um empregado a ser atualizado, atualizo ele e ponho ELE na lista de empregados empregados e empregadosPersistencia
    public static void alteraEmpregadoSindicalizado(String emp, String atributo, String valor, String idSindicato, String taxaSindical) throws IdentificacaoRepetida, IdSindicatoNula, TaxaNumerica, TaxaNegativa, TaxaNula, ValorTrueFalse {
        //System.out.println("ENTRA NO ALTERA SINDICALIZADO");
        Empregado atualizar = empregados.get(emp);

        if (valor.equals("false")) {
            atualizar.setSindicato(null);
        } else {

            if (idSindicato.isEmpty()) {
                throw new IdSindicatoNula();
            } else if (taxaSindical.isEmpty()) {
                throw new TaxaNula();
            } else if (taxaSindical.contains("-")) {
                throw new TaxaNegativa();
            } else if (!isNumeric(taxaSindical.replace(",", "."))) {
                throw new TaxaNumerica();
            } else if (!valor.equals("true") && !valor.equals("false")) {
                throw new ValorTrueFalse();
            } else if (sindicatos.containsKey(idSindicato)) throw new IdentificacaoRepetida();

            if (valor.equals("true")) {
                double taxaSindicalD = Double.parseDouble(taxaSindical.replace(",", "."));

                Sindicato sindicato = new Sindicato(idSindicato, taxaSindicalD);
                sindicatos.put(sindicato.getIdSindicato(), sindicato);
                atualizar.setSindicato(sindicato);

                empregados.put(emp, atualizar);
                empregadosPersistencia.add(atualizar);
            }

        }

    }

    //ALTERA GERAL - 3 variaveis
    public static void alteraAtributoEmpregado(String emp, String atributo, String valor1) throws Exception {
        // System.out.println("ENTRA NO ALTERA GERAL VALOR 1");

        if (!Verification.verificationEmpregado(emp))
            return;

        Empregado atualizar = empregados.get(emp);

        if (atributo.equals("nome")) {
            if (valor1.isEmpty()) {
                throw new NomeNulo();
            } else {
                atualizar.setNome(valor1);
            }


        } else if (atributo.equals("endereco")) {
            if (valor1.isEmpty()) {
                throw new EnderecoNulo();
            }
            atualizar.setEndereco(valor1);
        } else if (atributo.equals("tipo")) {
            if (!valor1.contains("horista") && !valor1.contains("assalariado") && !valor1.contains("comissionado")) {
                throw new TipoInvalido();
            }

            if (valor1.equals("horista")) {
                EmpregadoHorista empregadoHorista = new EmpregadoHorista(atualizar.getNome(),
                        atualizar.getEndereco(), atualizar.getSalario());

                empregados.replace(emp, empregadoHorista);
            } else if (valor1.equals("assalariado")) {
                EmpregadoAssalariado empregadoAssalariado = new EmpregadoAssalariado(atualizar.getNome(),
                        atualizar.getEndereco(), atualizar.getSalario());

                empregados.replace(emp, empregadoAssalariado);
            } else {
                EmpregadoComissionado empregadoComissionado = new EmpregadoComissionado(atualizar.getNome(),
                        atualizar.getEndereco(), atualizar.getSalario(), 0f);

                empregados.replace(emp, empregadoComissionado);
            }

            //ver se tem q mudar algo em outro lugar, pq se mudou o tipo deve fazer o cast

        } else if (atributo.equals("salario")) {

            if (valor1.isEmpty()) {
                throw new SalarioNulo();
            }
            if (!isNumeric(valor1.replace(",", "."))) {
                throw new SalarioNumerico();
            }
            if (valor1.contains("-")) {
                throw new SalarioPositivo();
            }

            double salarioDouble = Double.parseDouble(valor1.replace(",", "."));

            atualizar.setSalario(salarioDouble);
        } else if (atributo.equals("comissao")) {
            if (atualizar.getTipo() != "comissionado") {
                throw new NaoComissionado();
            }
            if (valor1.isEmpty()) {
                throw new ComissaoNula();
            }
            if (!isNumeric(valor1.replace(",", "."))) {
                throw new ComissaoNumerica();
            }
            if (valor1.contains("-")) {
                throw new ComissaoPositiva();
            } else {
                double comissaoDouble = Double.parseDouble(valor1.replace(",", "."));

                ((EmpregadoComissionado) atualizar).setComissao(comissaoDouble);

            }

        } else if (atributo.equals("metodoPagamento")) {
            if (!valor1.contains("emMaos") && !valor1.contains("correios") && !valor1.contains("banco")) {
                throw new MetodoInvalido();
            } else {

                if (valor1.contains("emMaos")) {
                    atualizar.setMetodoPagamento(new MetodoPagamentoEmMaos());
                } else if (valor1.contains("correios")) {
                    atualizar.setMetodoPagamento(new MetodoPagamentoCorreios());
                } else {
                    atualizar.setMetodoPagamento(new MetodoPagamentoBanco());
                }


            }
        } else if (atributo.equals("sindicalizado")) {
            if (valor1.equals("true")) {
                atualizar.setSindicato(new Sindicato());
            } else if (valor1.equals("false")) {
                atualizar.setSindicato(null);
            } else {
                throw new ValorTrueFalse();
            }
        } else if (atributo.equals("agendaPagamento")) {
            if (!valor1.equals("semanal 5") && !valor1.equals("mensal $") && !valor1.equals("semanal 2 5")) {
                throw new AgendaNaoDisponivel();
            } else {
                atualizar.setAgendaPagamento(valor1);
            }
        } else {
            throw new AtributoNaoExiste();
        }

        empregados.put(emp, atualizar);
        empregadosPersistencia.add(atualizar);
    }

    //4 variaveis, AQUI ELE MUDA O TIPO DE EMPREGADO E JA POE UM NOVO VALOR E SALARIO OU DE COMISSAO OU ALGO ASSIM
    public static void alteraEmpregado(String emp, String atributo, String valor, String comissao) throws Exception {
        //System.out.println("ENTRA NO ALTERA QUE RECEBE A COMISSAO");


        if (!Verification.verificationEmpregado(emp))
            return;

        Empregado atualizar = empregados.get(emp);
        if (atributo.equals("tipo")) {
            if (valor.equals("comissionado")) {
                EmpregadoComissionado comissionado = new EmpregadoComissionado(atualizar.getNome(), atualizar.getEndereco(), atualizar.getSalario(), 0.0);
                comissionado.setId(atualizar.getId());

                double comissaoDouble = Double.parseDouble(comissao.replace(".", ","));

                comissionado.setComissao(comissaoDouble);
                empregados.replace(emp, comissionado);
                empregadosPersistencia.add(comissionado);
            } else if (valor.equals("horista")) {
                EmpregadoHorista empregadoHorista = new EmpregadoHorista(atualizar.getNome(), atualizar.getEndereco(), atualizar.getSalario());
                empregadoHorista.setId(atualizar.getId());
                double salarioDouble = Double.parseDouble(comissao.replace(",", "."));

                atualizar.setSalario(salarioDouble);
                empregados.replace(emp, empregadoHorista);
                empregadosPersistencia.add(empregadoHorista);
            }
        }

        if (!valor.equals("comissionado")) {
            empregados.put(emp, atualizar);
            empregadosPersistencia.add(atualizar);
        }

    }


    //6 variaveis
    public static void adicionaMetodoPagamento(String emp, String atributo, String valor1, String banco, String agencia, String contaCorrente) throws ContaNula, AgenciaNula, BancoNulo, EmpregadoNaoExisteException, IdentificacaoNula {
        //System.out.println("ENTRA NO ALTERA DE METODO DE PAGAMENTO");
        MetodoPagamentoBanco bancoobj = new MetodoPagamentoBanco(banco, agencia, contaCorrente);
        if (banco.isEmpty()) {
            throw new BancoNulo();
        }
        if (agencia.isEmpty()) {
            throw new AgenciaNula();
        }
        if (contaCorrente.isEmpty()) {
            throw new ContaNula();
        }

        if (!Verification.verificationEmpregado(emp))
            return;

        Empregado atualizar = empregados.get(emp);

        atualizar.setMetodoPagamento(bancoobj);

        empregadosPersistencia.add(atualizar);
        empregados.put(emp, atualizar);
    }
}



