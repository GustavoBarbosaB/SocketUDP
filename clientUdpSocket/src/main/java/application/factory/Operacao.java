package application.factory;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.logging.Logger;

public class Operacao {

    private BigInteger chave;
    private String valor;
    private Integer comando;
    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);


    public Operacao(BigInteger chave, String valor, Integer comando) {
        this.chave = chave;
        this.valor = valor;
        this.comando = comando;
    }

    public byte[] convertData(){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        Integer tamChave;
        Integer tamMsg = 0;


        try {
            tamChave = chave.toByteArray().length;
            if (valor != null)
                tamMsg = valor.getBytes("UTF-16").length-2;

            //Escreve o comando
            dos.write(comando);
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
            if(valor != null)
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
