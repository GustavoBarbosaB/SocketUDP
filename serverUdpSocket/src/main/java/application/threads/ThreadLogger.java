package application.threads;

import application.helper.DataStorage;
import application.helper.FileStorageHelper;
import application.model.Operacao;

public class ThreadLogger extends Thread {

    private static ThreadLogger threadLogger;

    private ThreadLogger(){
    }

    static void init() {
        if(threadLogger == null) {
            threadLogger = new ThreadLogger();
            threadLogger.start();
        }
    }

    @Override
    public void run(){
        while(true) {
            if(!DataStorage.getInstance().getLog().isEmpty()) {

                Operacao op = DataStorage.getInstance().pollLog();
                updateLogFile(op);
            }
        }
    }

    private void updateLogFile(Operacao operacao) {
        FileStorageHelper.getInstance().saveLogData(operacao);
    }
}
