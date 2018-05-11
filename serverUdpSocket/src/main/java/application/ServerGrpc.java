package application;

import io.grpc.Server;
import io.grpc.netty.NettyServerBuilder;

import java.io.IOException;

public class ServerGrpc {
    private Server server;

    private void start(){
        try {
            server = NettyServerBuilder
                    .forPort(5959)
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
        server.blockUntilShutdown();
    }
}
