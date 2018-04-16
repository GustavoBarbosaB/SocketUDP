package application.threads;

import application.helper.DataStorage;
import application.helper.SerializeEstado;
import application.model.Operacao;

import java.io.IOException;
import java.net.*;

public class ThreadProcess extends Thread {

    private int responsePort;
    private DatagramSocket serverSocket;

    public ThreadProcess(int port, DatagramSocket serverSocket) {
        responsePort=port;
        this.serverSocket=serverSocket;
    }

    @Override
    public void run(){
        String mResposta = "Processado!";
        byte[] resposta = mResposta.getBytes();

        DatagramPacket sendPacket = null;
        try {
            Operacao op = SerializeEstado.readOperacao(DataStorage.getInstance().pollArriving());
            System.out.println(op.toString());
            sendPacket = new DatagramPacket(resposta, resposta.length, InetAddress.getByName("localhost"), responsePort);
            serverSocket.send(sendPacket);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
