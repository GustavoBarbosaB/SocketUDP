package application;

import application.configuration.ApplicationProperties;
import io.grpc.Server;
import io.grpc.netty.NettyServerBuilder;

import java.io.IOException;
import java.util.logging.Logger;

public class ThreadServerGrpc extends Thread{
    private Server server;
    private static String PORT;
    private static String IPADDRESS;
    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public void starter(){
        try {
            PORT = ApplicationProperties.getInstance().loadProperties().getProperty("servergrpc.port");
            IPADDRESS = ApplicationProperties.getInstance().loadProperties().getProperty("server.address");
            server = NettyServerBuilder
                    .forPort(Integer.parseInt(PORT))
                    .addService(new GrpcSocketImp())
                    .build()
                    .start();
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                    System.err.println("*** shutting down gRPC server since JVM is shutting down");
                    ThreadServerGrpc.this.stoper();
                    System.err.println("*** server shut down");
                }
            });
        }catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }
    public void stoper() {
        if (server != null) {
            server.shutdown();
        }
    }
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    @Override
    public void run(){
        final ThreadServerGrpc server = new ThreadServerGrpc();
        server.starter();
        logger.info("Porta do server = "+ PORT);
        try {
            server.blockUntilShutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
