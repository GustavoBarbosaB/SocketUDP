package application.helper;

import application.model.Arriving;
import application.model.Operacao;

import java.math.BigInteger;
import java.util.*;

public class DataStorage {
    private HashMap<BigInteger,String> executed;
    private HashMap<BigInteger,ArrayList<Integer>> registerHash;
    private Queue<Operacao> toLog;
    private Queue<Long> toRun;
    private Queue<Arriving> arriving;

    public synchronized String getExecuted(BigInteger chave) {
        return executed.get(chave);
    }

    public synchronized String addExecuted(BigInteger chave, String value) {
        if(!executed.containsKey(chave))
            executed.put(chave,value);
        else
            return "Chave existente!";

        return "Inserido com sucesso!";
    }

    public synchronized String replaceExecuted(BigInteger chave, String value){
        if(executed.containsKey(chave))
            executed.replace(chave,value);
        else
            return "Chave inexistente!";

        return "Atualizado com sucesso!";
    }

    private static DataStorage dataStorage;

    private DataStorage(){
        executed = new HashMap<BigInteger,String>();
        toLog = new LinkedList<Operacao>();
        toRun =  new LinkedList<Long>();
        arriving = new LinkedList<Arriving>();
        registerHash = new HashMap<BigInteger, ArrayList<Integer>>();
    }

    public synchronized static DataStorage getInstance(){
        if(dataStorage==null)
            dataStorage = new DataStorage();

        return dataStorage;
    }

    public synchronized String addRegisterHash(BigInteger chave, Integer port){
        ArrayList<Integer> listaClientes;
        if(!executed.containsKey(chave))
            return "Chave ainda não criada";

        if(!registerHash.containsKey(chave)){
            listaClientes = new ArrayList<Integer>();
            listaClientes.add(port);
            registerHash.put(chave,listaClientes);
        }
        else{
            listaClientes = registerHash.get(chave);
            listaClientes.add(port);
            registerHash.put(chave, listaClientes);
        }
        return "Registrado com sucesso!";
    }

    public synchronized ArrayList<Integer> getRegisterHash(BigInteger chave){
        ArrayList<Integer> listaClientes;
        if(!registerHash.containsKey(chave)){
            return null;
        }
        else{
            return registerHash.get(chave);
        }
    }

    public synchronized void addLog(Operacao o){
        toLog.add(o);
    }

    public synchronized Long pollToRun() {
        return toRun.poll();
    }

    public synchronized Long getFirstToRun() {
        return toRun.peek();
    }

    public synchronized void addToRun(Long pid) {
        this.toRun.add(pid);
    }

    public synchronized Operacao pollLog(){
        return toLog.poll();
    }

    public synchronized void addArriving(Arriving o){
        arriving.add(o);
    }

    public synchronized Arriving pollArriving(){
        return arriving.poll();
    }

    public synchronized void removeExecuted(BigInteger chave) {
        executed.remove(chave);
    }

    public Queue<Arriving> getArriving() {
        return arriving;
    }

    public Queue<Operacao> getLog() {
        return toLog;
    }
}
