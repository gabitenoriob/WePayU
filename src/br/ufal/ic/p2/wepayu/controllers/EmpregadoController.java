package br.ufal.ic.p2.wepayu.controllers;

import br.ufal.ic.p2.wepayu.Exception.EmpregadoAtributosExceptions;
import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.models.empregados.Assalariado;
import br.ufal.ic.p2.wepayu.models.empregados.Comissionado;
import br.ufal.ic.p2.wepayu.models.empregados.Empregado;
import br.ufal.ic.p2.wepayu.models.empregados.Horista;

import java.util.HashMap;

public class EmpregadoController {
    public static HashMap<String, Empregado> empregados = new HashMap<>();

    public  static String getAtributoEmpregado(String emp, String atributo) throws EmpregadoAtributosExceptions, EmpregadoNaoExisteException{
        if(emp.isEmpty()){
            throw new EmpregadoAtributosExceptions("Identificacao do empregado nao pode ser nula.");
        }
        if(!(empregados.containsKey(emp))){
            throw new EmpregadoAtributosExceptions("Empregado nao existe.");
        }
        if(!(empregados.containsKey(atributo))){
            throw new EmpregadoAtributosExceptions("Atributo nao existe.");
        }
        Empregado empregado = empregados.get(emp);
        return switch (atributo) {
            case "nome" -> empregado.getNome();
            case "endereco" -> empregado.getEndereco();
            case "tipo" -> empregado.getTipo();
            case "salario" -> empregado.getSalario();
            /* Nesse caso, empregado é uma instância de Comissionado e também uma instância de Empregado (devido à herança)
Quando você usa instanceof, está verificando se o objeto referenciado por uma variável é uma instância de uma classe específica ou de uma de suas subclasses.*/
            case "comissao" -> {
                if (empregado instanceof Comissionado) {
                    yield ((Comissionado) empregado).getComissao();
                } else {
                    throw new EmpregadoAtributosExceptions("Empregado não é do tipo Comissionado.");
                }
            }
            default -> throw new EmpregadoAtributosExceptions("Atributo nao existe.");
        };


    }
    public static String criarEmpregado(String nome, String endereco, String tipo, String salario) throws EmpregadoAtributosExceptions, EmpregadoNaoExisteException {

        //CRIAR EMPREGADOS DO TIPO ASSALARIADO E HORISTA

        if (tipo.equals("assalariado")){
            if (nome.isBlank()){
                throw new EmpregadoAtributosExceptions("Nome nao pode ser nulo.");
            }
            if (endereco.isBlank()) {
                throw new EmpregadoAtributosExceptions("Endereco nao pode ser nulo.");
            }
            if (!tipo.equalsIgnoreCase("horista") && !tipo.equalsIgnoreCase("assalariado") && !tipo.equalsIgnoreCase("comissionado")) {
                throw new EmpregadoAtributosExceptions("Tipo invalido.");
            }
            if (tipo.equalsIgnoreCase("comissionado")){
                throw new EmpregadoAtributosExceptions("Tipo nao aplicavel");
            }
            if (salario.isEmpty()) throw new EmpregadoAtributosExceptions("Salario nao pode ser nulo.");
            if (!isNumeric(salario)) {
                throw new EmpregadoAtributosExceptions("Salario deve ser numerico.");
            }
            if (Double.parseDouble(salario) < 0) {
                throw new EmpregadoAtributosExceptions("Salario deve ser nao-negativo.");
            }



            Empregado empregado = new Assalariado(nome,endereco,tipo,salario);
            String id = empregado.addEmpregado();
            empregados.put(empregado.addEmpregado(), empregado);
            return  id;

        } else if (tipo.equals("horista")) {

            if (nome.isBlank()){
                throw new EmpregadoAtributosExceptions("Nome nao pode ser nulo.");
            }
            if (endereco.isBlank()) {
                throw new EmpregadoAtributosExceptions("Endereco nao pode ser nulo.");
            }
            if (salario.isEmpty()) throw new EmpregadoAtributosExceptions("Salario nao pode ser nulo.");
            if (!isNumeric(salario)) {
                throw new EmpregadoAtributosExceptions("Salario deve ser numerico.");
            }
            if (Double.parseDouble(salario) < 0) {
                throw new EmpregadoAtributosExceptions("Salario deve ser nao-negativo.");
            }
            if (!tipo.equalsIgnoreCase("horista") && !tipo.equalsIgnoreCase("assalariado") && !tipo.equalsIgnoreCase("comissionado")) {
                throw new EmpregadoAtributosExceptions("Tipo invalido.");
            }
            if (tipo.equalsIgnoreCase("comissionado")){
                throw new EmpregadoAtributosExceptions("Tipo nao aplicavel");
            }



            Empregado empregado= new Horista(nome,endereco,tipo,salario);
           String id = empregado.addEmpregado();
            //O ADDEMPREGADO SO TA SERVINDO P PEGAR ID
            empregados.put(id, empregado);
            return id;


        }
        else{
            throw new EmpregadoAtributosExceptions("Tipo invalido.");
        }




    }


    public static String criarEmpregado(String nome, String endereco, String tipo, String salario, String comissao) throws EmpregadoNaoExisteException, EmpregadoAtributosExceptions {
        if (tipo.equals("comissionado")){
            if (nome.isBlank()){
                throw new EmpregadoAtributosExceptions("Nome nao pode ser nulo.");
            }
            if (endereco.isBlank()) {
                throw new EmpregadoAtributosExceptions("Endereco nao pode ser nulo.");
            }
            if (!tipo.equalsIgnoreCase("horista") && !tipo.equalsIgnoreCase("assalariado") && !tipo.equalsIgnoreCase("comissionado")) {
                throw new EmpregadoAtributosExceptions("Tipo invalido.");
            }
            if (tipo.equalsIgnoreCase("horista") || tipo.equalsIgnoreCase("assalariado")){
                throw new EmpregadoAtributosExceptions("Tipo nao aplicavel");
            }
            if (salario.isEmpty()) throw new EmpregadoAtributosExceptions("Salario nao pode ser nulo.");
            if (!isNumeric(salario)) {
                throw new EmpregadoAtributosExceptions("Salario deve ser numerico.");
            }
            if (Double.parseDouble(salario) < 0) {
                throw new EmpregadoAtributosExceptions("Salario deve ser nao-negativo.");
            }
            if (comissao.isEmpty()) throw new EmpregadoAtributosExceptions("Comissao nao pode ser nula.");
            if (!isNumeric(comissao)) {
                throw new EmpregadoAtributosExceptions("Comissao deve ser numerica.");
            }
            if (Double.parseDouble(comissao) < 0) {
                throw new EmpregadoAtributosExceptions("Comissao deve ser nao-negativa.");
            }



            Empregado empregado = new Comissionado(nome,endereco,tipo,salario,comissao);
            //O ADDEMPREGADO SO TA SERVINDO P PEGAR ID
            String id = empregado.addEmpregado();
            empregados.put(id, empregado);
            return id;

        }
        else{
            throw new EmpregadoAtributosExceptions("Tipo invalido.");
        }


    }


    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}