package application;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.math.BigInteger;

public class SerializeEstado {

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
