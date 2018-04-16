package application.helper;

import application.model.Operacao;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class DataStorage {

    private HashMap<BigInteger,String> executed;
    private Queue<Operacao> toLog;
    private Queue<Operacao> toRun;
    private Queue<byte[]> arriving;

    private static DataStorage dataStorage;

    private DataStorage(){
        executed = new HashMap<>();
        toLog = new LinkedList<>();
        toRun =  new LinkedList<>();
        arriving = new LinkedList<>();
    }

    public synchronized static DataStorage getInstance(){
        if(dataStorage==null)
            dataStorage = new DataStorage();

        return dataStorage;
    }

    public synchronized void addOperation(Operacao o){
        addLog(o);
        addRun(o);
    }

    private synchronized void addLog(Operacao o){
        toLog.add(o);
    }

    private synchronized void addRun(Operacao o){
        toRun.add(o);
    }

    public synchronized Operacao pollLog(){
        return toLog.poll();
    }

    public synchronized void addArriving(byte[] o){
        arriving.add(o);
    }

    public synchronized byte[] pollArriving(){
        return arriving.poll();
    }

    public synchronized Operacao pollRun(){
        return toRun.poll();
    }



}
