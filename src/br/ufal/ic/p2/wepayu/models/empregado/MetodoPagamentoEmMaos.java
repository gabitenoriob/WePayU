package br.ufal.ic.p2.wepayu.models.empregado;

public class MetodoPagamentoEmMaos extends MetodoPagamento{

    @Override
    public String getMetodoPagamento() {
        return "emMaos";
    }
}
