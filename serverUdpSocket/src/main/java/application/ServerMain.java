package application;

public class ServerMain {

    public static void main(String[] args) {
        Thread threadMainServer = new ThreadServerMain();
        threadMainServer.start();
        Thread threadMainGrpc = new ThreadServerGrpc();
        threadMainGrpc.start();
    }

}
