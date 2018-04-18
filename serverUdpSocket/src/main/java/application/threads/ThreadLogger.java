package application.threads;

import application.helper.FileStorageHelper;
import application.model.Operacao;

import static application.helper.DataStorage.getInstance;

public class ThreadLogger extends Thread {

    ThreadLogger(){
    }

    @Override
    public void run(){
        while(true) {
            if(!getInstance().getLog().isEmpty()) {

                Operacao op = getInstance().pollLog();
                updateLogFile(op);
            }
        }
    }

    private void updateLogFile(Operacao operacao) {
        FileStorageHelper.getInstance().saveLogData(operacao);
    }
}
