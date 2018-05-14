package application.helper;

import application.model.Operacao;
import application.threads.ThreadProcessSocket;
import io.grpc.stub.StreamObserver;
import org.socketUdp.grpc.OperationResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class ExecuteHelper extends Thread {


    public static String executeOperation(Operacao op){
        byte[] resposta;

        switch (op.getOperacao()) {
            case 0://Create
                resposta = DataStorage.getInstance().addExecuted(op.getChave(), op.getValor()).getBytes();
                DataStorage.getInstance().addLog(op);
                break;
            case 1://Read
                resposta = DataStorage.getInstance().getExecuted(op.getChave()).getBytes();
                DataStorage.getInstance().addLog(op);
                break;

            case 2://Update
                resposta = DataStorage.getInstance().replaceExecuted(op.getChave(), op.getValor()).getBytes();
                DataStorage.getInstance().addLog(op);
                notifyAllClients(op.getChave());
                break;

            case 3://Delete
                DataStorage.getInstance().removeExecuted(op.getChave());
                resposta = "Deletado com sucesso!".getBytes();
                DataStorage.getInstance().addLog(op);
                notifyAllClients(op.getChave());
                break;

            default:
                resposta = "Operação inexistente!".getBytes();
        }
        String response = null;

        try {
            response = new String(resposta, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return response;
    }

    public static void respondClientGrpc(StreamObserver<OperationResponse> responseGrpc, String resposta){
        OperationResponse response = OperationResponse.newBuilder()
                .setResponse(resposta)
                //.setValor("//E ESSE CAMPO ??//")
                .build();

        responseGrpc.onNext(response);
    }


    public static void respondClientSocket(DatagramSocket serverSocket,
                                           byte[] resposta,
                                           Integer port){
        try {
            DatagramPacket sendPacket = new DatagramPacket(resposta,
                    resposta.length,
                    InetAddress.getByName("localhost"),
                    port);
            serverSocket.send(sendPacket);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void notifyAllClients(BigInteger chave){
       ArrayList<Integer> clientsSocket = DataStorage.getInstance().getRegisterHashSocket(chave);
       ArrayList<StreamObserver<OperationResponse>> clientsGrpc = DataStorage.getInstance().getRegisterHashGrpc(chave);
       String resposta = "A chave "+chave+" foi alterada";

       //Responde os sockets
       for(Integer client:clientsSocket) {
           respondClientSocket(ThreadProcessSocket.getServerSocket(),
                   resposta.getBytes(),
                   client);
       }

       //Responde os grpc
        for(StreamObserver<OperationResponse> client:clientsGrpc){
           respondClientGrpc(client,
                   resposta);
        }
    }
}
