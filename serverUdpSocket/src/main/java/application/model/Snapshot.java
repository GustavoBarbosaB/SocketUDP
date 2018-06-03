package application.model;

import io.grpc.stub.StreamObserver;
import org.socketUdp.grpc.OperationResponse;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

public class Snapshot implements Serializable {

    private ConcurrentHashMap<BigInteger, ArrayList<StreamObserver<OperationResponse>>> registerHashGrpc;
    private ConcurrentHashMap<BigInteger, ArrayList<Integer>> registerHashSocket;
    private BlockingQueue<ArrivingSocket> arrivingSocket;
    private BlockingQueue<ArrivingGrpc> arrivingGrpc;

    public Snapshot() {}

    public Snapshot(ConcurrentHashMap<BigInteger, ArrayList<StreamObserver<OperationResponse>>> registerHashGrpc,
                    ConcurrentHashMap<BigInteger, ArrayList<Integer>> registerHashSocket,
                    LinkedBlockingDeque<ArrivingSocket> arrivingSocket,
                    LinkedBlockingDeque<ArrivingGrpc> arrivingGrpc) {
        this.registerHashGrpc = registerHashGrpc;
        this.registerHashSocket = registerHashSocket;
        this.arrivingSocket = arrivingSocket;
        this.arrivingGrpc = arrivingGrpc;
    }

    public ConcurrentHashMap<BigInteger, ArrayList<StreamObserver<OperationResponse>>> getRegisterHashGrpc() {
        return registerHashGrpc;
    }

    public void setRegisterHashGrpc(ConcurrentHashMap<BigInteger, ArrayList<StreamObserver<OperationResponse>>> registerHashGrpc) {
        this.registerHashGrpc = registerHashGrpc;
    }

    public ConcurrentHashMap<BigInteger, ArrayList<Integer>> getRegisterHashSocket() {
        return registerHashSocket;
    }

    public void setRegisterHashSocket(ConcurrentHashMap<BigInteger, ArrayList<Integer>> registerHashSocket) {
        this.registerHashSocket = registerHashSocket;
    }

    public Queue<ArrivingSocket> getArrivingSocket() {
        return arrivingSocket;
    }

    public void setArrivingSocket(LinkedBlockingDeque<ArrivingSocket> arrivingSocket) {
        this.arrivingSocket = arrivingSocket;
    }

    public Queue<ArrivingGrpc> getArrivingGrpc() {
        return arrivingGrpc;
    }

    public void setArrivingGrpc(LinkedBlockingDeque<ArrivingGrpc> arrivingGrpc) {
        this.arrivingGrpc = arrivingGrpc;
    }
}
