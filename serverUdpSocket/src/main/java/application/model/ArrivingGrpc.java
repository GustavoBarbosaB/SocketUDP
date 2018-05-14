package application.model;

import io.grpc.stub.StreamObserver;
import org.socketUdp.grpc.Operation;
import org.socketUdp.grpc.OperationResponse;

public class ArrivingGrpc {

    private StreamObserver<OperationResponse> responseGrpc;
    private Operation requestGrpc;

    public Operation getRequestGrpc() {
        return requestGrpc;
    }

    public void setRequestGrpc(Operation requestGrpc) {
        this.requestGrpc = requestGrpc;
    }

    public StreamObserver<OperationResponse> getResponseGrpc() {

        return responseGrpc;
    }

    public void setResponseGrpc(StreamObserver<OperationResponse> responseGrpc) {
        this.responseGrpc = responseGrpc;
    }

    public ArrivingGrpc(Operation requestGrpc, StreamObserver<OperationResponse> responseGrpc) {

        this.responseGrpc = responseGrpc;
        this.requestGrpc = requestGrpc;
    }


}
