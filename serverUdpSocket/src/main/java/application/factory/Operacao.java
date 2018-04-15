package application.factory;

public class Operacao {

    private Integer chave;
    private String valor;
    private Integer operacao;


    public Operacao() {
    }

    public Operacao(Integer chave, String valor, Integer operacao) {
        this.chave = chave;
        this.valor = valor;
        this.operacao = operacao;
    }
}
