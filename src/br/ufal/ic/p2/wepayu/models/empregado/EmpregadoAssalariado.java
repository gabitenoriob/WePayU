package br.ufal.ic.p2.wepayu.models.empregado;

public class EmpregadoAssalariado extends Empregado {

    public EmpregadoAssalariado () {

    }
    public EmpregadoAssalariado(String nome, String endereco, double salario) throws Exception {
        super(nome, endereco, salario, "mensal $");
    }

    @Override
    public String getTipo() {
        return "assalariado";
    }
}
