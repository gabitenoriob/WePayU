package br.ufal.ic.p2.wepayu.models.empregado;

public abstract class Empregado {
    private String nome;
    private String endereco;
    private double salario;

    private String id;
    private Sindicato sindicato;
    private MetodoPagamento metodoPagamento;
    private String agendaPagamento;

    public Empregado() {
    }

    public Empregado(String nome, String endereco, double salario, String agendaPagamento)
        {
            this.nome = nome;
            this.endereco = endereco;
            this.salario = salario;
            this.id = null;
            this.sindicato = null;
            this.metodoPagamento = new MetodoPagamentoEmMaos();
            this.agendaPagamento = agendaPagamento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Sindicato getSindicato() {
        return sindicato;
    }

    public void setSindicato(Sindicato sindicato) {
        this.sindicato = sindicato;
    }

    public MetodoPagamento getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(MetodoPagamento metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }
   
    public String getAgendaPagamento() {
    
        return agendaPagamento;
    }

    public void setAgendaPagamento(String agendaPagamento) {
        this.agendaPagamento = agendaPagamento;
    }

    public abstract String getTipo();
}
