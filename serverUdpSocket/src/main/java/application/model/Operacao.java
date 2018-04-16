package application.model;

import java.math.BigInteger;

public class Operacao {

    private BigInteger chave;
    private String valor;
    private Integer operacao;


    public Operacao() {
    }

    public BigInteger getChave() {
        return chave;
    }

    public void setChave(BigInteger chave) {
        this.chave = chave;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Integer getOperacao() {
        return operacao;
    }

    public void setOperacao(Integer operacao) {
        this.operacao = operacao;
    }

    public Operacao(BigInteger chave, String valor, Integer operacao) {
        this.chave = chave;
        this.valor = valor;
        this.operacao = operacao;
    }

    @Override
    public String toString(){
        return "Operacao: "+operacao+"\n"+
                "Chave: "+chave+"\n"+
                "Mensagem: "+valor+"\n";
    }
}
