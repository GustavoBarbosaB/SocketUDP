package application.helper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileStorageHelper {

    private File file;

    public FileStorageHelper(String fileName) {
        file = new File(fileName);
    }

    public <T> void saveData(List<T> list){
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
            } catch (IOException e) {
            }
        }
    }

    public <T> void saveLogData(T item){
        if(item != null) {
            List<T> list = recoverData();
            list.add(item);
            saveData(list);
        }
    }

    public <T> void saveData(T item){
        if(item != null) {
            try {
                FileOutputStream f = new FileOutputStream(file);
                ObjectOutputStream o = new ObjectOutputStream(f);

                o.writeObject(item);

                o.close();
                f.close();

            } catch (FileNotFoundException e) {
            } catch (IOException e) {
            }
        }
    }

    public <T> List<T> recoverListData() {
        List<T> list = new ArrayList<T>();
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
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public <T> T recoverData() {
        try {
            FileInputStream f = new FileInputStream(file);
            ObjectInputStream o = new ObjectInputStream(f);

            T item = (T) o.readObject();
            if(item != null) {
                o.close();
                f.close();
                return item;
            }

            o.close();
            f.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
