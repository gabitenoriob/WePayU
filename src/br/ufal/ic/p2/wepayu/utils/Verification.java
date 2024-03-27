package br.ufal.ic.p2.wepayu.utils;

import br.ufal.ic.p2.wepayu.exceptions.*;
import br.ufal.ic.p2.wepayu.models.empregado.Empregado;

import static br.ufal.ic.p2.wepayu.controllers.SistemaController.empregados;
import static br.ufal.ic.p2.wepayu.utils.IsNumeric.isNumeric;

public class Verification {
    public static boolean verificationCriarEmpregado(String nome, String endereco, String salario) throws NomeNulo, EnderecoNulo, SalarioNulo, SalarioPositivo, SalarioNumerico {

        if (nome.isEmpty()) {
            throw new NomeNulo();
        }
        if (endereco.isBlank()) {
            throw new EnderecoNulo();
        }

        if (salario.isBlank()) {
            throw new SalarioNulo();
        }
        if (salario.contains("-")) {
            throw new SalarioPositivo();
        }
        if (!isNumeric(salario.replace(",", "."))) {
            throw new SalarioNumerico();
        }

        return true;
    }

    public static boolean verificationEmpregado(String emp) throws IdentificacaoNula, EmpregadoNaoExisteException {
        Empregado buscado = empregados.get(emp);

        if (emp.isEmpty()) {
            throw new IdentificacaoNula();
        } else if (buscado == null) {
            throw new EmpregadoNaoExisteException();
        }

        return true;
    }

}
