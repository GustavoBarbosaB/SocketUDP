package application;

import application.configuration.ApplicationProperties;

import java.io.IOException;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class ServerMain {

    static String port;
    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    ArrayList<byte[]> arriving;

    public static void main(String args[])
    {
        port = ApplicationProperties.getInstance().loadProperties().getProperty("server.port");
        logger.info("Porta do server = "+port);

        byte[] receiveData = new byte[1480];
        DatagramSocket serverSocket = null;

        while(true) {
            try {
                serverSocket = new DatagramSocket(Integer.parseInt(port));
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);

                byte[] result = receivePacket.getData();

                byte[] opBy = SerializeEstado.getBytes(result, 0, 1);
                Integer op = SerializeEstado.readInt(opBy);


                byte[] tamChaveBy = SerializeEstado.getBytes(result, 1, 1);
                Integer tamChave = SerializeEstado.readInt(tamChaveBy);

                byte[] chaveBy = SerializeEstado.getBytes(result, 2, tamChave);
                BigInteger chave = SerializeEstado.readBigInt(chaveBy);

                byte[] tamValorBy = SerializeEstado.getBytes(result, 2+tamChave, 1);
                Integer tamValor = SerializeEstado.readInt(tamValorBy);

                byte[] valorBy = SerializeEstado.getBytes(result, 2+tamChave+1, tamValor);
                String valor = SerializeEstado.readString(valorBy);

                logger.info("Operacao: " + op);
                logger.info("Tamanho chave: " + tamChave);
                logger.info("Chave: " + chave);
                logger.info("Tamanho valor: " + tamValor);
                logger.info("Valor: " + valor);

                String resposta = "RECEBIDO COM SUCESSO";
                byte[] dados = resposta.getBytes("UTF-8");

                DatagramPacket sendPacket = new DatagramPacket(dados, dados.length, InetAddress.getByName("localhost"), 5003);

                serverSocket.send(sendPacket);


            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                serverSocket.close();
            }
        }

    }

}
