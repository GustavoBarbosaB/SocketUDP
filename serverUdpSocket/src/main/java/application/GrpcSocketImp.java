package application;

import application.model.Operacao;
import io.grpc.stub.StreamObserver;
import org.socketUdp.grpc.MakeOperationGrpc;
import org.socketUdp.grpc.Operation;
import org.socketUdp.grpc.OperationResponse;

import java.io.IOException;
import java.math.BigInteger;

import static application.helper.DataStorage.getInstance;

public class GrpcSocketImp extends MakeOperationGrpc.MakeOperationImplBase {
    @Override
    public void makeOperation(
        Operation request, StreamObserver<OperationResponse> responseObserver) {

        BigInteger chaveNova;
        Integer chave = request.getChave();
        try{
            chaveNova = BigInteger.valueOf(chave.intValue());
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            return;
        }

            Operacao op = new Operacao();
            op.setChave(chaveNova);
            op.setOperacao(request.getOp());
            op.setValor(request.getValor());

            OperationResponse response = OperationResponse.newBuilder()
                    .setResponse("MENSAGEM VINDA DO SERVIDOR//")
                    .setValor("ATE O MOMENTO NADA")
                    .build();

            responseObserver.onNext(response);
            try {
                sendToQueue(op.convertData());
            } catch (IOException e) {
                e.printStackTrace();
            }
            responseObserver.onCompleted();
    }

    public void sendToQueue(byte[] dados) throws IOException {



    }



}
