package application.threads;

import application.helper.FileStorageHelper;

public class ThreadSnapshot extends Thread {

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
        while(true) {

        }
    }
}
