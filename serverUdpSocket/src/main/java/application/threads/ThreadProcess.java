package application.threads;

import application.helper.SerializeEstado;
import application.model.Arriving;
import application.model.Operacao;

import java.io.IOException;
import java.net.*;
import java.util.List;
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

        ThreadLogger threadLogger = ThreadLogger.init();
        List<Operacao> operacaos = threadLogger.getLogList();
        if(operacaos != null) {
            for (Operacao operacao : operacaos) {
                ThreadExecute.executeOperation(operacao);
                String message = "EXECUTE:" + "Op:" + operacao.getOperacao()
                        + " Chave: "+ operacao.getChave()
                        + " Valor: "+ operacao.getValor();
                System.out.println(message);
            }
        }

        while(true) {
            try {
                if(!getInstance().getArriving().isEmpty()) {

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
