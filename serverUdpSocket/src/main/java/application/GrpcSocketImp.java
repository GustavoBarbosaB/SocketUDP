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

}
