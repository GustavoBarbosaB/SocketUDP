package application.threads;

import application.helper.ExecuteHelper;
import application.helper.SerializeEstado;
import application.model.ArrivingSocket;
import application.model.Operacao;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.logging.Logger;

import static application.helper.DataStorage.getInstance;

public class ThreadProcessSocket extends Thread {

    public static DatagramSocket getServerSocket() {
        return serverSocket;
    }

    private static DatagramSocket serverSocket;
    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public ThreadProcessSocket(DatagramSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {

        ThreadLogger threadLogger = ThreadLogger.init();
        List<Operacao> operacaos = threadLogger.getLogList();
        if (operacaos != null) {
            for (Operacao operacao : operacaos) {
                executeOperation(operacao);
                String message = "EXECUTE:" + "Op:" + operacao.getOperacao()
                        + " Chave: " + operacao.getChave()
                        + " Valor: " + operacao.getValor();
                System.out.println(message);
            }
        }

        while (true) {
            try {
                if (!getInstance().getArrivingSocket().isEmpty()) {
                    String resposta;

                    ArrivingSocket arrivingSocket = getInstance().pollArrivingSocket();
                    Operacao op = SerializeEstado.readOperacao(arrivingSocket.getPackage());
                    op.setGrpc(false);

                    System.out.println(op.toString());

                    if(op.getOperacao()==4) {
                        resposta = getInstance().addRegisterHashSocket(op.getChave(), arrivingSocket.getmPort());
                    }else {
                        resposta = ExecuteHelper.executeOperation(op);
                    }

                    ExecuteHelper.respondClientSocket(serverSocket,
                            resposta.getBytes(),
                            arrivingSocket.getmPort());

                }
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    private static void executeOperation(Operacao operacao) {

        switch (operacao.getOperacao()) {

            case 0://Create
                getInstance().addExecuted(operacao.getChave(), operacao.getValor());
                break;

            case 1://Read
                getInstance().getExecuted(operacao.getChave());
                break;

            case 2://Update
                getInstance().replaceExecuted(operacao.getChave(), operacao.getValor());
                break;

            case 3://Delete
                getInstance().removeExecuted(operacao.getChave());
                break;

            default:
                break;
        }
    }
}
