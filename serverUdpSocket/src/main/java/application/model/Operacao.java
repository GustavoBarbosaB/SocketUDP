package application.model;

import java.io.*;
import java.math.BigInteger;

public class Operacao implements Serializable {

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
    public String toString() {
        return "Operacao: " + operacao + "\n" +
                "Chave: " + chave + "\n" +
                "Mensagem: " + valor + "\n";
    }

    public byte[] convertData() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        Integer tamChave;
        Integer tamMsg = 0;


        try {
            tamChave = chave.toByteArray().length;
            if (valor != null)
                tamMsg = valor.getBytes("UTF-16").length - 2;

            //Escreve o operacao
            dos.write(operacao);
            //logger.info("1 byte comando");
            //Escreve o tamanho da chave
            dos.write(tamChave);
            //logger.info("1 byte tamanho da chave");
            //Escreve a chave
            dos.write(chave.toByteArray());
            //logger.info(tamChave+" byte chave");
            //Escreve o tamanho da mensagem
            dos.write(tamMsg);
            //logger.info("1 byte tamanho da mensagem");
            //Escreve a mensagem
            if (valor != null)
                dos.writeChars(valor);
//            logger.info(bos.toByteArray().length+" bytes da operacao toda");

            dos.flush();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bos.toByteArray();
    }
}
