package application;

import application.model.ArrivingGrpc;
import application.model.Operacao;
import application.threads.ThreadProcessGrpc;
import io.grpc.stub.StreamObserver;
import io.grpc.stub.StreamObservers;
import org.socketUdp.grpc.MakeOperationGrpc;
import org.socketUdp.grpc.Operation;
import org.socketUdp.grpc.OperationResponse;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

import static application.helper.DataStorage.getInstance;

public class GrpcSocketImp extends MakeOperationGrpc.MakeOperationImplBase {

    @Override
    public void makeOperation(Operation request, StreamObserver<OperationResponse> responseObserver) {

        getInstance().addArrivingGrpc(new ArrivingGrpc(request, responseObserver));
        ThreadProcessGrpc threadProcessGrpc = new ThreadProcessGrpc();
        threadProcessGrpc.start();


    }

    public String execute(Operacao op) throws IOException {
        System.out.println(op.toString());
        byte[] resposta;
        String str;
        try {
            switch (op.getOperacao()) {
                case 0://Create
                    resposta = getInstance().addExecuted(op.getChave(), op.getValor()).getBytes();
                    getInstance().addLog(op);
                    break;
                case 1://Read
                    resposta = getInstance().getExecuted(op.getChave()).getBytes();
                    if (resposta == null) resposta = "Chave não existe".getBytes();
                    getInstance().addLog(op);
                    break;
                case 2://Update
                    resposta = getInstance().replaceExecuted(op.getChave(), op.getValor()).getBytes();
                    getInstance().addLog(op);
                    break;
                case 3://Delete
                    getInstance().removeExecuted(op.getChave());
                    resposta = "Deletado com sucesso!".getBytes();
                    getInstance().addLog(op);
                    break;
                case 4://Register
                    getInstance().removeExecuted(op.getChave());
                    resposta = "Deletado com sucesso!".getBytes();
                    getInstance().addLog(op);
                    break;
                default:
                    resposta = "Operação inexistente!".getBytes();
            }
            str = new String(resposta, "UTF-8");
            return str;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


}
