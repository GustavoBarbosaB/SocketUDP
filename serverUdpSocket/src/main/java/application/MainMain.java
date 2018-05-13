package application;

import application.threads.ThreadProcess;

import java.io.IOException;

public class MainMain {

    public static void main(String[] args) throws IOException, InterruptedException {
        Thread threadMainServer = new ThreadServerMain();
        threadMainServer.start();
        Thread threadMainGrpc = new ThreadServerGrpc();
        threadMainGrpc.start();
    }

}
