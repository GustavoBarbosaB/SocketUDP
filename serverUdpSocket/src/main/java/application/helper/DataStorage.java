package application.helper;

import application.model.ArrivingGrpc;
import application.model.ArrivingSocket;
import application.model.Operacao;
import io.grpc.stub.StreamObserver;
import org.socketUdp.grpc.OperationResponse;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

public class DataStorage {

    private ConcurrentHashMap<BigInteger, String> executed;
    private ConcurrentHashMap<BigInteger, ArrayList<StreamObserver<OperationResponse>>> registerHashGrpc;
    private ConcurrentHashMap<BigInteger, ArrayList<Integer>> registerHashSocket;
    private BlockingQueue<Operacao> toLog;
    private BlockingQueue<ArrivingSocket> arrivingSocket;
    private BlockingQueue<ArrivingGrpc> arrivingGrpc;

    private static DataStorage dataStorage;

    private DataStorage() {
        executed = new ConcurrentHashMap<BigInteger, String>();
        toLog = new LinkedBlockingDeque<Operacao>();
        arrivingSocket = new LinkedBlockingDeque<ArrivingSocket>();
        arrivingGrpc = new LinkedBlockingDeque<ArrivingGrpc>();
        registerHashGrpc = new ConcurrentHashMap<BigInteger, ArrayList<StreamObserver<OperationResponse>>>();
        registerHashSocket = new ConcurrentHashMap<BigInteger, ArrayList<Integer>>();

    }

    public synchronized static DataStorage getInstance() {
        if (dataStorage == null)
            if (dataStorage == null)
                dataStorage = new DataStorage();

        return dataStorage;
    }

    public synchronized String getExecuted(BigInteger chave) {
        if (executed.containsKey(chave))
            return executed.get(chave);

        return "Chave inexistente!";
    }

    public synchronized String addExecuted(BigInteger chave, String value) {
        if (!executed.containsKey(chave)) {
            executed.put(chave, value);
            return "Inserido com sucesso!";
        }

        return "Chave existente!";
    }

    public synchronized String replaceExecuted(BigInteger chave, String value) {
        if (executed.containsKey(chave)) {
            executed.replace(chave, value);
            return "Atualizado com sucesso!";
        }

        return "Chave inexistente!";

    }

    public synchronized String addRegisterHashSocket(BigInteger chave, Integer port) {
        ArrayList<Integer> listaClientes;
        if (!executed.containsKey(chave))
            return "Chave ainda não criada";

        if (!registerHashSocket.containsKey(chave)) {
            listaClientes = new ArrayList<Integer>();
            listaClientes.add(port);
            registerHashSocket.put(chave, listaClientes);
        } else {
            listaClientes = registerHashSocket.get(chave);
            listaClientes.add(port);
            registerHashSocket.put(chave, listaClientes);
        }
        return "Registrado com sucesso!";
    }


    public synchronized ArrayList<Integer> getRegisterHashSocket(BigInteger chave) {
        return registerHashSocket.get(chave);

    }

    public synchronized String addRegisterHashGrpc(BigInteger chave, StreamObserver<OperationResponse> ouvinte) {
        ArrayList<StreamObserver<OperationResponse>> listaClientes;
        if (!executed.containsKey(chave))
            return "Chave ainda não existente";

        if (!registerHashGrpc.containsKey(chave)) {
            registerHashGrpc.put(chave, new ArrayList<StreamObserver<OperationResponse>>(Collections.singleton(ouvinte)));
        } else {
            listaClientes = registerHashGrpc.get(chave);
            listaClientes.add(ouvinte);
            registerHashGrpc.put(chave, listaClientes);
        }
        return "Registrado com sucesso!";
    }


    public synchronized ArrayList<StreamObserver<OperationResponse>> getRegisterHashGrpc(BigInteger chave) {
        return registerHashGrpc.get(chave);
    }

    public synchronized void addLog(Operacao o) {
        toLog.add(o);
    }


    public synchronized Operacao pollLog() {
        return toLog.poll();
    }

    public synchronized void addArrivingSocket(ArrivingSocket o) {
        arrivingSocket.add(o);
    }

    public synchronized ArrivingSocket pollArrivingSocket() {
        return arrivingSocket.poll();
    }

    public synchronized void addArrivingGrpc(ArrivingGrpc o) {
        arrivingGrpc.add(o);
    }

    public synchronized ArrivingGrpc pollArrivingGrpc() {
        return arrivingGrpc.poll();
    }


    public synchronized void removeExecuted(BigInteger chave) {
        executed.remove(chave);
        registerHashSocket.remove(chave);
        registerHashGrpc.remove(chave);
    }

    public synchronized Queue<Operacao> getLog() {
        return toLog;
    }

    public synchronized ConcurrentHashMap<BigInteger, ArrayList<StreamObserver<OperationResponse>>> getRegisterHashGrpc() {
        return registerHashGrpc;
    }

    public synchronized void setRegisterHashGrpc(ConcurrentHashMap<BigInteger, ArrayList<StreamObserver<OperationResponse>>> registerHashGrpc) {
        this.registerHashGrpc = registerHashGrpc;
    }

    public synchronized ConcurrentHashMap<BigInteger, ArrayList<Integer>> getRegisterHashSocket() {
        return registerHashSocket;
    }

    public synchronized void setRegisterHashSocket(ConcurrentHashMap<BigInteger, ArrayList<Integer>> registerHashSocket) {
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
