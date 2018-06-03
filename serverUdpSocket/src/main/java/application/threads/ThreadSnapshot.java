package application.threads;

import application.helper.FileStorageHelper;
import application.model.Snapshot;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadSnapshot extends Thread {

    private static final long TIME_DELAY = 5;        //MINUTES
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
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //TODO Save snapshot
            }
        };
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(runnable, TIME_DELAY, TIME_INTERVAL, TimeUnit.MINUTES);
    }

    private void updateLogFile(Snapshot snapshot) {
        fileStorageHelper.saveLogData(snapshot);
    }

    Snapshot getSnapshot() {
        return (Snapshot) fileStorageHelper.recoverData();
    }
}
