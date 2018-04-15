package application;

import application.configuration.ApplicationProperties;
import application.model.Operacao;

import java.io.IOException;
import java.io.Serializable;
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

                System.out.println(receivePacket.getData());
                System.out.println(receivePacket.getData().length);

                byte[] result = receivePacket.getData();
                byte[] b1 = SerializeEstado.getBytes(result, 0, 1);
                int inteiro = SerializeEstado.readInt(b1);


                byte[] b2 = SerializeEstado.getBytes(result, 1, inteiro);
                String string = SerializeEstado.readString(b2);

                logger.info("inteiro: " + inteiro);
                logger.info("string: " + string);


                System.out.println("RECEIVED: " + inteiro);
                System.out.println("RECEIVED: " + string);


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
