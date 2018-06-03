package application.threads;

import application.helper.DataStorage;
import application.helper.FileStorageHelper;
import application.model.Snapshot;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadSnapshot extends Thread {

    private static final long TIME_DELAY = 5;           //MINUTES
    private static final long TIME_INTERVAL = 5;        //MINUTES
    private static final String FILE_NAME = "server_snapshot.txt";

    private static ThreadSnapshot threadSnapshot;
    private FileStorageHelper fileStorageHelper;

    private ThreadSnapshot(){
        fileStorageHelper = new FileStorageHelper(FILE_NAME);
    }

    static ThreadSnapshot init() {
        if(threadSnapshot == null) {
            threadSnapshot = new ThreadSnapshot();
            threadSnapshot.start();
        }
        return threadSnapshot;
    }

    @Override
    public void run(){
        System.out.println("Prepare to save the snapshot");
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Snapshot snapshot = new Snapshot(
                        DataStorage.getInstance().getExecuted(),
                        DataStorage.getInstance().getRegisterHashGrpc(),
                        DataStorage.getInstance().getRegisterHashSocket(),
                        DataStorage.getInstance().getArrivingSocket(),
                        DataStorage.getInstance().getArrivingGrpc()
                );
                updateLogFile(snapshot);
                System.out.println("SAVE THE SNAPSHOT");
                ThreadLogger.clearLog();
            }
        };
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(runnable, TIME_DELAY, TIME_INTERVAL, TimeUnit.MINUTES);
    }

    private void updateLogFile(Snapshot snapshot) {
        fileStorageHelper.saveData(snapshot);
    }

    Snapshot getSnapshot() {
        return (Snapshot) fileStorageHelper.recoverData();
    }
}
