package br.ufal.ic.p2.wepayu.models.empregado;

public class MetodoPagamentoCorreios extends MetodoPagamento {

    public MetodoPagamentoCorreios() {

    }

    @Override
    public String getMetodoPagamento() {
        return "correios";
    }
}