package application.threads;

import application.helper.ExecuteHelper;
import application.model.ArrivingGrpc;
import application.model.Operacao;
import org.socketUdp.grpc.Operation;

import java.math.BigInteger;
import java.util.logging.Logger;

import static application.helper.DataStorage.getInstance;

public class ThreadProcessGrpc extends Thread {

    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static ThreadProcessGrpc threadProcessGrpc;

    private ThreadProcessGrpc() {

    }

    public static ThreadProcessGrpc init(){
        if(threadProcessGrpc==null) {
            threadProcessGrpc = new ThreadProcessGrpc();
            threadProcessGrpc.start();
        }
        return threadProcessGrpc;
    }

    @Override
    public void run() {

        while (true) {
            if (!getInstance().getArrivingGrpc().isEmpty()) {
                String response;
                ArrivingGrpc arrivingGrpc = getInstance().pollArrivingGrpc();
                Operation operationGrpc = arrivingGrpc.getRequestGrpc();
                Operacao op = convertToOp(operationGrpc);

                System.out.println(op.toString());

                if (op.getOperacao()==4)
                    response = getInstance().addRegisterHashGrpc(op.getChave(),arrivingGrpc.getResponseGrpc());

                else {
                    response = ExecuteHelper.executeOperation(op);
                }

                ExecuteHelper.respondClientGrpc(arrivingGrpc.getResponseGrpc(), response);
            }
        }

    }

    private Operacao convertToOp(Operation request) {
        Operacao op = new Operacao();

        Integer chave = request.getChave();
        BigInteger chaveBigInteger = BigInteger.valueOf(chave.intValue());

        op.setChave(chaveBigInteger);
        op.setOperacao(request.getOp());
        op.setValor(request.getValor());
        op.setGrpc(true);

        return op;
    }

}
