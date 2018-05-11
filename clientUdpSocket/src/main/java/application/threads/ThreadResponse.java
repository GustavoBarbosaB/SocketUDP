package application.threads;

import application.configuration.ApplicationProperties;
import application.factory.Operacao;

import java.io.IOException;
import java.net.*;
import java.util.logging.Logger;

public class ThreadResponse extends Thread{

    private Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    DatagramSocket clientSocket = null;


    public ThreadResponse (DatagramSocket socket){
        this.clientSocket = socket;
    }

    @Override
    public void run(){

        byte[] receivedData = new byte[1480];

        while(true){
            try {

                // MENSAGEM VINDA DO SERVIDOR
                DatagramPacket receivedPacket = new DatagramPacket(receivedData, receivedData.length);
                clientSocket.receive(receivedPacket);
                String data = new String(receivedPacket.getData(), receivedPacket.getOffset(), receivedPacket.getLength());
                System.out.println("Resposta do servidor: " + data);

            } catch (SocketException e) {
                e.printStackTrace();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }




    }

}
