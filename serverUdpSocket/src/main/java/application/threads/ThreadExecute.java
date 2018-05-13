package application.threads;

import application.helper.DataStorage;
import application.model.Operacao;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

import static application.helper.DataStorage.getInstance;

public class ThreadExecute extends Thread{

    private Integer port;
    private DatagramSocket serverSocket;
    private Operacao op;
    private ArrayList<Integer> clientesRegistrados;

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
                    resposta = getInstance().addExecuted(op.getChave(), op.getValor()).getBytes();
                    sendPacket = new DatagramPacket(resposta, resposta.length, InetAddress.getByName("localhost"), port);
                    getInstance().addLog(op);
                    break;
                case 1://Read
                    resposta = getInstance().getExecuted(op.getChave()).getBytes();
                    if(resposta == null) resposta = "Chave inexistente".getBytes();
                    //TODO quando vc pesquisa uma chave que nao existe ta retornando erro "Exception in thread "Thread-3" java.lang.NullPointerException"
                    sendPacket = new DatagramPacket(resposta, resposta.length, InetAddress.getByName("localhost"), port);
                    getInstance().addLog(op);
                    break;

                case 2://Update
                    resposta = getInstance().replaceExecuted(op.getChave(), op.getValor()).getBytes();
                    clientesRegistrados = getInstance().getRegisterHash((op.getChave()));
                    if (clientesRegistrados != null){
                        for(Integer i = 0; i < clientesRegistrados.size(); i++){
                            byte[] respostaRegistro = ("**O valor da chave " + op.getChave() + " foi alterado para: "+ op.getValor()).getBytes();
                            Integer portaCliente = clientesRegistrados.get(i);
                            sendPacket = new DatagramPacket(respostaRegistro, respostaRegistro.length, InetAddress.getByName("localhost"), portaCliente);
                            serverSocket.send(sendPacket);
                        }
                    }
                    sendPacket = new DatagramPacket(resposta, resposta.length, InetAddress.getByName("localhost"), port);
                    getInstance().addLog(op);
                    break;

                case 3://Delete
                    clientesRegistrados = getInstance().getRegisterHash((op.getChave()));
                    if (clientesRegistrados != null){
                        for(Integer i = 0; i < clientesRegistrados.size(); i++){
                            byte[] respostaRegistro = ("**A chave " + op.getChave() + " foi removida").getBytes();
                            Integer portaCliente = clientesRegistrados.get(i);
                            sendPacket = new DatagramPacket(respostaRegistro, respostaRegistro.length, InetAddress.getByName("localhost"), portaCliente);
                            serverSocket.send(sendPacket);
                        }
                    }
                    getInstance().removeRegisterHash((op.getChave()));
                    getInstance().removeExecuted(op.getChave());
                    resposta = "Deletado com sucesso!".getBytes();
                    sendPacket = new DatagramPacket(resposta, resposta.length, InetAddress.getByName("localhost"), port);
                    getInstance().addLog(op);
                    break;

                case 4://Register
                    resposta = getInstance().addRegisterHash(op.getChave(), port).getBytes();
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
