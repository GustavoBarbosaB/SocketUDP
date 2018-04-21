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

        DatagramPacket sendPacket;
        byte[] resposta;

        try {
            switch (op.getOperacao()) {
                case 0://Create
                    getInstance().addExecuted(op.getChave(), op.getValor());
                    resposta = "Criado com sucesso!".getBytes();
                    sendPacket = new DatagramPacket(resposta, resposta.length, InetAddress.getByName("localhost"), port);
                    getInstance().addLog(op);
                    break;
                case 1://Read
                    resposta = getInstance().getExecuted(op.getChave()).getBytes();
                    sendPacket = new DatagramPacket(resposta, resposta.length, InetAddress.getByName("localhost"), port);
                    getInstance().addLog(op);
                    break;

                case 2://Update
                    getInstance().addExecuted(op.getChave(), op.getValor());
                    resposta = "Atualizado com sucesso!".getBytes();
                    sendPacket = new DatagramPacket(resposta, resposta.length, InetAddress.getByName("localhost"), port);
                    getInstance().addLog(op);
                    break;

                case 3://Delete
                    getInstance().removeExecuted(op.getChave());
                    resposta = "Deletado com sucesso!".getBytes();
                    sendPacket = new DatagramPacket(resposta, resposta.length, InetAddress.getByName("localhost"), port);
                    getInstance().addLog(op);
                    break;

                default:
                    resposta = "Operação inexistente!".getBytes();
                    sendPacket = new DatagramPacket(resposta, resposta.length, InetAddress.getByName("localhost"), port);

            }
            serverSocket.send(sendPacket);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    static void executeOperation(Operacao operacao){

        switch (operacao.getOperacao()){

            case 0://Create
                getInstance().addExecuted(operacao.getChave(),operacao.getValor());
                break;

            case 1://Read
                getInstance().getExecuted(operacao.getChave());
                break;

            case 2://Update
                getInstance().addExecuted(operacao.getChave(),operacao.getValor());
                break;

            case 3://Delete
                getInstance().removeExecuted(operacao.getChave());
                break;

            default:
                break;
        }
    }

}
