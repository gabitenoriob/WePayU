package br.ufal.ic.p2.wepayu.models.empregado;


public class EmpregadoComissionado extends Empregado {
    private double comissao;

    public EmpregadoComissionado(){
    }
    public EmpregadoComissionado(String nome, String endereco, double salario, double comissao) throws Exception {
        super(nome, endereco, salario, "semanal 2 5");
        this.comissao = comissao;
        
    }

    public double getComissao() {
        return comissao;
    }
    public void setComissao(double comissao) {
        this.comissao = comissao;
    }
    public double getSalarioBruto(String dataInicial, String data) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSalarioBruto'");
    }

    @Override
    public String getTipo() {
        return "comissionado";
    }
}
