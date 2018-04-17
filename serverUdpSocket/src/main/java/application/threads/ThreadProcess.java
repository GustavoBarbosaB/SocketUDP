package application.threads;

import application.helper.DataStorage;
import application.helper.SerializeEstado;
import application.model.Operacao;

import java.io.IOException;
import java.net.*;
import java.util.logging.Logger;

public class ThreadProcess extends Thread {

    private int responsePort;
    private DatagramSocket serverSocket;
    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);


    public ThreadProcess(int port, DatagramSocket serverSocket) {
        responsePort=port;
        this.serverSocket=serverSocket;
    }

    @Override
    public void run(){

        try {
            Operacao op = SerializeEstado.readOperacao(DataStorage.getInstance().pollArriving());
            System.out.println(op.toString());
            executeOperation(op);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void executeOperation(Operacao op){

        switch (op.getOperacao()){
            case 0://Create
                DataStorage.getInstance().addExecuted(op.getChave(),op.getValor());
                break;
            case 1://Read
                try {
                    byte[] resposta = DataStorage.getInstance().getExecuted(op.getChave()).getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(resposta, resposta.length, InetAddress.getByName("localhost"), responsePort);
                    serverSocket.send(sendPacket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case 2://Update
                DataStorage.getInstance().addExecuted(op.getChave(),op.getValor());
                break;

            case 3://Delete
                DataStorage.getInstance().removeExecuted(op.getChave());
                break;

            default:
                logger.warning("Comando desconhecido!");
        }
    }


}
