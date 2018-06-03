package application.model;

import io.grpc.stub.StreamObserver;
import org.socketUdp.grpc.OperationResponse;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

public class Snapshot implements Serializable {

    private long version;
    private ConcurrentHashMap<BigInteger, String> executed;
    private ConcurrentHashMap<BigInteger, ArrayList<StreamObserver<OperationResponse>>> registerHashGrpc;
    private ConcurrentHashMap<BigInteger, ArrayList<Integer>> registerHashSocket;
    private BlockingQueue<ArrivingSocket> arrivingSocket;
    private BlockingQueue<ArrivingGrpc> arrivingGrpc;

    public Snapshot() {}

    public Snapshot(long version,
                    ConcurrentHashMap<BigInteger, String> executed,
                    ConcurrentHashMap<BigInteger, ArrayList<StreamObserver<OperationResponse>>> registerHashGrpc,
                    ConcurrentHashMap<BigInteger, ArrayList<Integer>> registerHashSocket,
                    LinkedBlockingDeque<ArrivingSocket> arrivingSocket,
                    LinkedBlockingDeque<ArrivingGrpc> arrivingGrpc) {
        this.version = version;
        this.executed = executed;
        this.registerHashGrpc = registerHashGrpc;
        this.registerHashSocket = registerHashSocket;
        this.arrivingSocket = arrivingSocket;
        this.arrivingGrpc = arrivingGrpc;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public ConcurrentHashMap<BigInteger, String> getExecuted() {
        return executed;
    }

    public void setExecuted(ConcurrentHashMap<BigInteger, String> executed) {
        this.executed = executed;
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

    public  LinkedBlockingDeque<ArrivingSocket> getArrivingSocket() {
        return (LinkedBlockingDeque<ArrivingSocket>) arrivingSocket;
    }

    public void setArrivingSocket(LinkedBlockingDeque<ArrivingSocket> arrivingSocket) {
        this.arrivingSocket = arrivingSocket;
    }

    public LinkedBlockingDeque<ArrivingGrpc> getArrivingGrpc() {
        return (LinkedBlockingDeque<ArrivingGrpc>) arrivingGrpc;
    }

    public void setArrivingGrpc(LinkedBlockingDeque<ArrivingGrpc> arrivingGrpc) {
        this.arrivingGrpc = arrivingGrpc;
    }
}
