package br.ufal.ic.p2.wepayu.models.empregado;

public class MetodoPagamentoBanco extends MetodoPagamento {
    private String banco;
    private String agencia;
    private String contaCorrente;

    public MetodoPagamentoBanco() {

    }

    public MetodoPagamentoBanco(String banco, String agencia, String contaCorrente) {
        this.banco = banco;
        this.agencia = agencia;
        this.contaCorrente = contaCorrente;
    }

    public String getBanco() {
        return banco;
    }

    public String getAgencia() {
        return agencia;
    }

    public String getContaCorrente() {
        return contaCorrente;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public void setContaCorrente(String contaCorrente) {
        this.contaCorrente = contaCorrente;
    }

    @Override
    public String getMetodoPagamento() {
        return "banco";
    }
}