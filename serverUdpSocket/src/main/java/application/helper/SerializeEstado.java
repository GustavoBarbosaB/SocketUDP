package application.helper;

import application.model.Operacao;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.logging.Logger;

public class SerializeEstado {

    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static Operacao readOperacao(byte[] data) throws IOException {

        byte[] opBy = SerializeEstado.getBytes(data, 0, 1);
        Integer op = SerializeEstado.readInt(opBy);

        byte[] tamChaveBy = SerializeEstado.getBytes(data, 1, 1);
        Integer tamChave = SerializeEstado.readInt(tamChaveBy);

        byte[] chaveBy = SerializeEstado.getBytes(data, 2, tamChave);
        BigInteger chave = SerializeEstado.readBigInt(chaveBy);

        byte[] tamValorBy = SerializeEstado.getBytes(data, 2+tamChave, 1);
        Integer tamValor = SerializeEstado.readInt(tamValorBy);

        byte[] valorBy = SerializeEstado.getBytes(data, 2+tamChave+1, tamValor);
        String valor = SerializeEstado.readString(valorBy);

        return new Operacao(chave,valor,op);
    }

    public static String readString(byte[] b) throws IOException {
            return new String(b,"UTF-16");
    }

    public static BigInteger readBigInt(byte[] b) {
        return new BigInteger(b);
    }

    public static int readInt(byte[] b) {
        ByteArrayInputStream bis = new ByteArrayInputStream(b);
        DataInputStream in = new DataInputStream(bis);
        return bis.read();
    }

    public static byte[] getBytes(byte[] b,int init, int tam){
        byte[] ret = new byte[tam];
        int total = init+tam;
        for(int i=init;i<total;i++){
            ret[(i+tam)-total] = b[i];
        }
        return ret;
    }

}
