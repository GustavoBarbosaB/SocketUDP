package application;

import application.model.Operacao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class DataStorage {

    private HashMap<BigInteger,String> executed;
    private Queue<Operacao> toLog;
    private Queue<Operacao> toRun;
    private static DataStorage dataStorage;

    private DataStorage(){
        executed = new HashMap<>();
        toLog = new LinkedList<>();
        toRun =  new LinkedList<>();
    }

    public static DataStorage getInstance(){
        return dataStorage!=null?dataStorage:(new DataStorage());
    }

    public void addOperation(Operacao o){
        addLog(o);
        addRun(o);
    }

    private void addLog(Operacao o){
        toLog.add(o);
    }

    private void addRun(Operacao o){
        toRun.add(o);
    }

    public Operacao getLog(){
        return toLog.poll();
    }

    public Operacao getRun(){
        return toRun.poll();
    }



}
