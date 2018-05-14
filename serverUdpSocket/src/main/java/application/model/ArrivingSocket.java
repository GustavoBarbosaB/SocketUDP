package application.model;

public class ArrivingSocket {

    public byte[] getPackage() {
        return mPackage;
    }

    public void setPackage(byte[] mPackage) {
        this.mPackage = mPackage;
    }

    public Integer getmPort() {

        return mPort;
    }

    public void setmPort(Integer mPort) {
        this.mPort = mPort;
    }

    public ArrivingSocket(Integer mPort, byte[] mPackage) {

        this.mPort = mPort;
        this.mPackage = mPackage;
    }

    private Integer mPort;
    private byte[] mPackage;
}
