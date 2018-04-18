package application.helper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileStorageHelper {

    private static final String FILE_NAME = "server_log.txt";

    private static File file;
    private static FileStorageHelper fileStorageHelper;

    private FileStorageHelper() {
        init();
    }

    private static void init() {
        file = new File(FILE_NAME);
    }

    public static FileStorageHelper getInstance() {
        if(fileStorageHelper == null) {
            fileStorageHelper = new FileStorageHelper();
        }
        return fileStorageHelper;
    }

    public static <T> void saveLogData(List<T> list){
        if(list != null) {
            try {
                FileOutputStream f = new FileOutputStream(file);
                ObjectOutputStream o = new ObjectOutputStream(f);

                for (T item : list) {
                    o.writeObject(item);
                }

                o.close();
                f.close();

            } catch (FileNotFoundException e) {
                System.out.println("File not found");
            } catch (IOException e) {
                System.out.println("Error initializing stream");
            }
        }
    }

    public static <T> void saveLogData(T item){
        if(item != null) {
            try {
                FileOutputStream f = new FileOutputStream(file);
                ObjectOutputStream o = new ObjectOutputStream(f);

                o.writeObject(item);

                o.close();
                f.close();

            } catch (FileNotFoundException e) {
                System.out.println("File not found");
            } catch (IOException e) {
                System.out.println("Error initializing stream");
            }
        }
    }

    public static <T> List<T> recoverLogData() {
        List<T> list = new ArrayList<>();
        try {
            FileInputStream f = new FileInputStream(file);
            ObjectInputStream o = new ObjectInputStream(f);

            boolean isRead = true;
            do {
                T item = (T) o.readObject();
                if(item != null) {
                    list.add(item);
                } else {
                    isRead = false;
                }
            } while(isRead);

            o.close();
            f.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }
}
