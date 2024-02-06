package br.ufal.ic.p2.wepayu.models.empregados;

import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.Exception.EmpregadoAtributosExceptions;

import java.util.HashMap;
import java.util.UUID;
import java.util.ArrayList;

public class Empregado {
    private String nome;
    private String endereco;
    private String tipo;
    private String salario;
    private boolean sindicalizado;



    public Empregado(String nome, String endereco, String tipo, String salario) throws EmpregadoNaoExisteException {
        this.nome = nome;
        this.endereco = endereco;
        this.tipo = tipo;
        this.salario = salario;


    }


    public boolean getSindicalizado() {
        return sindicalizado;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getTipo() {
        return tipo;
    }

    public String getSalario() {
        salario = salario.replace(',', '.');
        return salario;
    }


    public String addEmpregado() throws EmpregadoAtributosExceptions, EmpregadoNaoExisteException {


        String id = UUID.randomUUID().toString();
        return id;
    }



}
