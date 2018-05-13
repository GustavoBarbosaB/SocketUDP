package application.model;

import java.io.*;
import java.math.BigInteger;

public class Operacao implements Serializable {

    private BigInteger chave;
    private String valor;
    private Integer operacao;
    private Boolean isGrpc;

    public Boolean isGrpc() {
        return isGrpc;
    }

    public void setGrpc(Boolean grpc) {
        isGrpc = grpc;
    }

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
    public String toString() {
        return "Operacao: " + operacao + "\n" +
                "Chave: " + chave + "\n" +
                "Mensagem: " + valor + "\n"+
                "Grpc: "+isGrpc+"\n";
    }
    
}
