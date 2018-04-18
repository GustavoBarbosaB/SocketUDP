package application.threads;

import application.helper.DataStorage;
import application.model.Operacao;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import static application.helper.DataStorage.getInstance;

public class ThreadExecute extends Thread{

    private Integer port;
    private DatagramSocket serverSocket;
    private Operacao op;

    public ThreadExecute(DatagramSocket serverSocket, Integer port,Operacao op) {
        this.port=port;
        this.serverSocket=serverSocket;
        this.op = op;
    }

    @Override
    public void run(){

        while (true){
            if(DataStorage.getInstance().getFirstToRun().equals(this.getId())){
                executeOperation();
                DataStorage.getInstance().pollToRun();
                break;
            }
        }


    }

    private void executeOperation(){

        switch (op.getOperacao()){
            case 0://Create
                getInstance().addExecuted(op.getChave(),op.getValor());
                break;
            case 1://Read
                try {
                    byte[] resposta = getInstance().getExecuted(op.getChave()).getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(resposta, resposta.length, InetAddress.getByName("localhost"),port);
                    serverSocket.send(sendPacket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case 2://Update
                getInstance().addExecuted(op.getChave(),op.getValor());
                break;

            case 3://Delete
                getInstance().removeExecuted(op.getChave());
                break;

            default:
                //logger.warning("Comando desconhecido!");
        }
    }

}
