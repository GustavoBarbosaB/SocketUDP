package application.threads;

import application.helper.DataStorage;
import application.helper.FileStorageHelper;
import application.model.Snapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadSnapshot extends Thread {

    enum Backup {
        FILE_NAME_SNAPSHOT_0("server_snapshot_0.txt"),
        FILE_NAME_SNAPSHOT_1("server_snapshot_1.txt"),
        FILE_NAME_SNAPSHOT_2("server_snapshot_2.txt");

        private String value;

        Backup(String value) {
            this.value = value;
        }

        private String getValue() {
            return value;
        }
    }

    private static final long TIME_DELAY = 5;           //MINUTES
    private static final long TIME_INTERVAL = 5;        //MINUTES

    private static ThreadSnapshot threadSnapshot;

    private int currentPosition = 0;
    private long currentVersion = 0;

    private List<FileStorageHelper> fileStorageHelperList;

    private ThreadSnapshot(){
        fileStorageHelperList = new ArrayList<FileStorageHelper>();
        for (Backup backup : Backup.values()) {
            FileStorageHelper fileStorageHelper = new FileStorageHelper(backup.getValue());
            fileStorageHelperList.add(fileStorageHelper);
        }
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
                currentVersion++;
                Snapshot snapshot = new Snapshot(
                        currentVersion,
                        DataStorage.getInstance().getExecuted(),
                        DataStorage.getInstance().getRegisterHashGrpc(),
                        DataStorage.getInstance().getRegisterHashSocket(),
                        DataStorage.getInstance().getArrivingSocket(),
                        DataStorage.getInstance().getArrivingGrpc()
                );
                String message = "SAVE THE SNAPSHOT: file " + currentPosition + ", version " + currentVersion;
                System.out.println(message);
                updateLogFile(snapshot);
                ThreadLogger.clearLog();
            }
        };
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(runnable, TIME_DELAY, TIME_INTERVAL, TimeUnit.MINUTES);
    }

    private void updateLogFile(Snapshot snapshot) {
        fileStorageHelperList.get(currentPosition).saveData(snapshot);
        updatePosition();
    }

    private void updatePosition() {
        currentPosition++;
        if(currentPosition == fileStorageHelperList.size()) {
            currentPosition = 0;
        }
    }

    Snapshot getTheMostCurrentSnapshot() {
        long version = 0;
        int position = 0;
        Snapshot currentSnapshot = null;

        for(int index = 0; index < Backup.values().length; index++) {
            Snapshot snapshot = fileStorageHelperList.get(index).recoverData();
            if(snapshot != null && version < snapshot.getVersion()) {
               currentSnapshot = snapshot;
               version = snapshot.getVersion();
               position = index;
            }
        }

        currentVersion = version;
        currentPosition = position;

        if(currentSnapshot != null) {
            updatePosition();
        }

        return currentSnapshot;
    }
}
