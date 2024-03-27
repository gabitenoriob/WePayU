package br.ufal.ic.p2.wepayu.models.empregado;


public class EmpregadoHorista extends Empregado {
    public EmpregadoHorista(){
        super();
    }
    public EmpregadoHorista(String nome, String endereco, double salario) throws Exception {
        super(nome, endereco, salario, "semanal 5");
    }
    public Double getSalarioBruto(String dataInicial, String data) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSalarioBruto'");
    }

    @Override
    public String getTipo() {
        return "horista";
    }
}
