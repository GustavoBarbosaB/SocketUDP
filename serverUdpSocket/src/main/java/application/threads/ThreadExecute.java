package application.threads;

import application.helper.DataStorage;
import application.model.Operacao;
import io.grpc.stub.StreamObserver;
import org.socketUdp.grpc.OperationResponse;

import java.io.UnsupportedEncodingException;

public class ThreadExecute extends Thread {

    private Operacao op;
    private String resposta;
    private StreamObserver<OperationResponse> responseGrpc;
    private Integer port;

    public ThreadExecute(Operacao op, StreamObserver<OperationResponse> responseGrpc, Integer port) {
        this.op = op;
        this.responseGrpc = responseGrpc;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            resposta = executeOperation();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if(op.isGrpc())
            respondClientGrpc(resposta);
        //else
            //respondClientSocket(resposta);
    }

    private String executeOperation() throws UnsupportedEncodingException {

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
                break;

            case 3://Delete
                DataStorage.getInstance().removeExecuted(op.getChave());
                resposta = "Deletado com sucesso!".getBytes();
                DataStorage.getInstance().addLog(op);
                break;

            default:
                resposta = "Operação inexistente!".getBytes();
        }
        String str = new String(resposta, "UTF-8");
        return str;
    }

    public void respondClientGrpc(String resposta){
        OperationResponse response = OperationResponse.newBuilder()
                .setResponse(resposta)
                .setValor("//E ESSE CAMPO ??//")
                .build();

        responseGrpc.onNext(response);
    }
}
