package application;

import io.grpc.stub.StreamObserver;
import org.socketUdp.grpc.MakeOperationGrpc;
import org.socketUdp.grpc.Operation;
import org.socketUdp.grpc.OperationResponse;

public class GrpcSocketImp extends MakeOperationGrpc.MakeOperationImplBase {
    @Override
    public void makeOperation(
            Operation request, StreamObserver<OperationResponse> responseObserver) {

        String responseString = new StringBuilder()
                .append("Operacao: ")
                .append(request.getOp())
                .append("/ Chave: ")
                .append(request.getChave())
                .append("/ Valor: ")
                .append(request.getValor())
                .toString();

        OperationResponse response = OperationResponse.newBuilder()
                .setResponse(responseString)
                .setValor("ATE O MOMENTO NADA")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
