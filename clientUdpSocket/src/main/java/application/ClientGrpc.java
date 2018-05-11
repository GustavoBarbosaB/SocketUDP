package application;

import application.model.OpcoesMenu;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import org.socketUdp.grpc.MakeOperationGrpc;
import org.socketUdp.grpc.Operation;
import org.socketUdp.grpc.OperationResponse;

import java.math.BigInteger;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ClientGrpc {
    private final ManagedChannel channel;
    private final MakeOperationGrpc.MakeOperationBlockingStub blockingStub;

    public ClientGrpc(String host, int port){
        this(ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build());
    }

    ClientGrpc(ManagedChannel channel) {
        this.channel = channel;
        blockingStub = MakeOperationGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void makeOperation(int op, Integer chave, String valor) {
        Operation operation = Operation.newBuilder()
                .setOp(op)
                .setChave(chave)
                .setValor(valor)
                .build();
        OperationResponse response;
        try {
            response = blockingStub.makeOperation(operation);
        } catch (StatusRuntimeException e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println("DADOS: " + response.getResponse() + "/" + response.getValor());
    }


    public static void main(String[] args) throws InterruptedException {
        ClientGrpc client = new ClientGrpc("localhost", 5959);
        Scanner scanner = new Scanner(System.in);

        Integer opcaoMenu = 4;
        Integer chave = 0;
        String valor = "";
        try {


            while (true) {
                //------- MENU
                opcaoMenu = imprimirMenu(opcaoMenu, scanner);
                //----------------------------------
                if(opcaoMenu==10)
                    break;

                //------- RECEBER CHAVE E VALOR
                System.out.println("--------------------");
                System.out.println("Digite a chave:");
                chave = scanner.nextInt();

                if (opcaoMenu == OpcoesMenu.CREATE.getValor() || opcaoMenu == OpcoesMenu.UPDATE.getValor()) {
                    System.out.println("Digite o valor ");
                    valor = scanner.next();
                }

                client.makeOperation(opcaoMenu, chave, valor);
            }


        } finally {
            client.shutdown();
        }
    }

    public static int imprimirMenu(int opcao, Scanner scanner){
        do {
            OpcoesMenu[] menu = OpcoesMenu.values();
            for (OpcoesMenu m : menu) {
                System.out.printf("[%d] - %s%n", m.ordinal(), m.name());
            }
            System.out.println("Opção:");
            opcao = scanner.nextInt();
        }while ((opcao > 3 || opcao < 0) && opcao != 10);
        return opcao;
    }

}
