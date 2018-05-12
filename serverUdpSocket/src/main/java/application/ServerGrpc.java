package application;

import application.configuration.ApplicationProperties;
import io.grpc.Server;
import io.grpc.netty.NettyServerBuilder;

import java.io.IOException;
import java.util.logging.Logger;

public class ServerGrpc {
    private Server server;
    private static String PORT;
    private static String IPADDRESS;
    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private void start(){
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
                    ServerGrpc.this.stop();
                    System.err.println("*** server shut down");
                }
            });
        }catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }
    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }


    public static void main(String[] args) throws IOException, InterruptedException {
        final ServerGrpc server = new ServerGrpc();
        server.start();
        logger.info("Porta do server = "+ PORT);
        server.blockUntilShutdown();
    }
}
