package application.threads;

import application.helper.DataStorage;
import application.helper.SerializeEstado;
import application.model.Arriving;
import application.model.Operacao;

import java.io.IOException;
import java.net.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Logger;

import static application.helper.DataStorage.getInstance;

public class ThreadProcess extends Thread {

    private DatagramSocket serverSocket;
    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public ThreadProcess( DatagramSocket serverSocket) {
        this.serverSocket=serverSocket;
    }

    @Override
    public void run(){

        //Todo fazer a leitura do log


        while(true) {
            try {
                if(!getInstance().getArriving().isEmpty()) {

                    //Todo adicionar na fila de log

                    Arriving arriving = getInstance().pollArriving();
                    Operacao op = SerializeEstado.readOperacao(arriving.getPackage());
                    System.out.println(op.toString());
                    Thread thread = new ThreadExecute(serverSocket,arriving.getmPort(),op);
                    getInstance().addToRun(thread.getId());
                    thread.start();
                }
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }






}
