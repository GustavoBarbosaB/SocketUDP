package application.threads;

import application.helper.SerializeEstado;
import application.model.ArrivingGrpc;
import application.model.ArrivingSocket;
import application.model.Operacao;
import io.grpc.stub.StreamObserver;
import org.socketUdp.grpc.Operation;
import org.socketUdp.grpc.OperationResponse;

import java.io.IOException;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static application.helper.DataStorage.getInstance;

public class ThreadProcessGrpc extends Thread {

    private ArrayList<StreamObserver<OperationResponse>> clientesRegistradosGrpc;
    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public ThreadProcessGrpc() {

    }

    @Override
    public void run() {

        ThreadLogger threadLogger = ThreadLogger.init();
        List<Operacao> operacaos = threadLogger.getLogList();
        if (operacaos != null) {
            for (Operacao operacao : operacaos) {
                //executeOperation(operacao);
                String message = "EXECUTE:" + "Op:" + operacao.getOperacao()
                        + " Chave: " + operacao.getChave()
                        + " Valor: " + operacao.getValor();
                System.out.println(message);
            }
        }

        while (true) {
            if (!getInstance().getArrivingGrpc().isEmpty()) {

                ArrivingGrpc arrivingGrpc = getInstance().pollArrivingGrpc();
                Operation operationGrpc = arrivingGrpc.getRequestGrpc();
                Operacao op = convertToOp(operationGrpc);


                System.out.println(op.toString());

                ThreadExecute threadExecute = new ThreadExecute(op, arrivingGrpc.getResponseGrpc(), null);

                threadExecute.start();

            }
        }

    }

    private Operacao convertToOp(Operation request){
        Operacao op = new Operacao();

        Integer chave = request.getChave();
        BigInteger chaveBigInteger = BigInteger.valueOf(chave.intValue());

        op.setChave(chaveBigInteger);
        op.setOperacao(request.getOp());
        op.setValor(request.getValor());
        op.setGrpc(true);

        return op;
    }

//    private void executeOperation(Operacao op, Integer port) {
//
//        DatagramPacket sendPacket = null;
//        byte[] resposta;
//
//        try {
//            switch (op.getOperacao()) {
//                case 0://Create
//                    resposta = getInstance().addExecuted(op.getChave(), op.getValor()).getBytes();
//                    sendPacket = new DatagramPacket(resposta, resposta.length, InetAddress.getByName("localhost"), port);
//                    getInstance().addLog(op);
//                    break;
//                case 1://Read
//                    resposta = getInstance().getExecuted(op.getChave()).getBytes();
//                    sendPacket = new DatagramPacket(resposta, resposta.length, InetAddress.getByName("localhost"), port);
//                    getInstance().addLog(op);
//                    break;
//
//                case 2://Update
//                    resposta = getInstance().replaceExecuted(op.getChave(), op.getValor()).getBytes();
//
//                    //TODO Criar uma chamada pra avisar os clientes RPC e Socket
//                    /**REMOVER O CÓDIGO ABAIXO E MANDAR PARA UMA CLASSE OU MÉTODO
//                     * TANTO PARA CLIENTES GRPC QUANTO PARA SOCKET
//                     * **/
//                    clientesRegistradosGrpc = getInstance().getRegisterHashGrpc((op.getChave()));
//                    clientesRegistradosSocket = getInstance().getRegisterHashSocket((op.getChave()));
//                    if (clientesRegistradosSocket != null) {
//                        for (Integer i = 0; i < clientesRegistradosSocket.size(); i++) {
//                            byte[] respostaRegistro = ("**O valor da chave " + op.getChave() + " foi alterado para: " + op.getValor()).getBytes();
//                            Integer portaCliente = clientesRegistradosSocket.get(i);
//                            sendPacket = new DatagramPacket(respostaRegistro, respostaRegistro.length, InetAddress.getByName("localhost"), portaCliente);
//                            serverSocket.send(sendPacket);
//                        }
//                    }
//
//
//                    sendPacket = new DatagramPacket(resposta, resposta.length, InetAddress.getByName("localhost"), port);
//                    getInstance().addLog(op);
//                    break;
//
//                case 3://Delete
//                    clientesRegistradosSocket = getInstance().getRegisterHashSocket((op.getChave()));
//                    clientesRegistradosGrpc = getInstance().getRegisterHashGrpc((op.getChave()));
//
//                    //responde clientes socket
//                    if (clientesRegistradosSocket != null) {
//                        for (Integer i = 0; i < clientesRegistradosSocket.size(); i++) {
//                            byte[] respostaRegistro = ("**A chave " + op.getChave() + " foi removida").getBytes();
//                            Integer portaCliente = clientesRegistradosSocket.get(i);
//                            sendPacket = new DatagramPacket(respostaRegistro, respostaRegistro.length, InetAddress.getByName("localhost"), portaCliente);
//                            serverSocket.send(sendPacket);
//                        }
//                    }
//                    //TODO fazer resposta de clientes grpc
//
//
//                    getInstance().removeRegisterHashSocket((op.getChave()));
//                    getInstance().removeRegisterHashGrpc((op.getChave()));
//                    getInstance().removeExecuted(op.getChave());
//
//                    resposta = "Deletado com sucesso!".getBytes();
//
//                    sendPacket = new DatagramPacket(resposta, resposta.length, InetAddress.getByName("localhost"), port);
//                    getInstance().addLog(op);
//                    break;
//
//                case 4://Register
//                    resposta = getInstance().addRegisterHashSocket(op.getChave(), port).getBytes();
//                    sendPacket = new DatagramPacket(resposta, resposta.length, InetAddress.getByName("localhost"), port);
//                    getInstance().addLog(op);
//                    break;
//
//                default:
//                    resposta = "Operação inexistente!".getBytes();
//                    sendPacket = new DatagramPacket(resposta, resposta.length, InetAddress.getByName("localhost"), port);
//
//            }
//
//            serverSocket.send(sendPacket);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//    }

//    private static void executeOperation(Operacao operacao) {
//
//        switch (operacao.getOperacao()) {
//
//            case 0://Create
//                getInstance().addExecuted(operacao.getChave(), operacao.getValor());
//                break;
//
//            case 1://Read
//                getInstance().getExecuted(operacao.getChave());
//                break;
//
//            case 2://Update
//                getInstance().addExecuted(operacao.getChave(), operacao.getValor());
//                break;
//
//            case 3://Delete
//                getInstance().removeExecuted(operacao.getChave());
//                break;
//
//            default:
//                break;
//        }
//    }
}
