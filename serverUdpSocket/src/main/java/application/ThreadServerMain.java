package application;

import application.configuration.ApplicationProperties;
import application.helper.DataStorage;
import application.helper.SerializeEstado;
import application.model.Arriving;
import application.model.Operacao;
import application.threads.ThreadProcess;

import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Logger;

import static application.helper.DataStorage.*;
import static application.helper.SerializeEstado.*;

public class ThreadServerMain extends Thread{

    private static String PORT;
    private static String IPADDRESS;
    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @Override
    public void run()
    {
        PORT = ApplicationProperties.getInstance().loadProperties().getProperty("server.port");
        IPADDRESS = ApplicationProperties.getInstance().loadProperties().getProperty("server.address");
        logger.info("Porta do server = "+ PORT);

        byte[] receiveData = new byte[1480];
        DatagramSocket serverSocket = null;
        try {
            serverSocket = new DatagramSocket(Integer.parseInt(PORT));
        } catch (SocketException e) {
            e.printStackTrace();
        }
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

        Thread threadProcess = new ThreadProcess(serverSocket);
        threadProcess.start();

        while(true) {
            try {
                serverSocket.receive(receivePacket);

                byte[] result = receivePacket.getData();

                getInstance().addArriving(new Arriving(receivePacket.getPort(),result));


            } catch (IOException e) {
                e.printStackTrace();
            }


        }


    }

}
