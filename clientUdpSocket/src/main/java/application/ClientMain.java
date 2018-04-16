package application;

import application.configuration.ApplicationProperties;
import application.model.OpcoesMenu;
import application.model.Operacao;
import application.threads.ThreadProcess;

import java.math.BigInteger;
import java.util.Scanner;
import java.util.logging.Logger;

public class ClientMain {

    private static String port = ApplicationProperties.getInstance().
            loadProperties().getProperty("server.port");
    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static String IPADDRESS = "localhost";


    public static void main(String args[]) {
        Operacao operacao;

        Scanner scanner = new Scanner(System.in);

        //------- ATRIBUTOS A SEREM ENVIADOS
        Integer opcaoMenu = 4;
        BigInteger chave;
        String valor = null;
        //----------------------------------
        while (true) {
            //------- MENU
            opcaoMenu = imprimirMenu(opcaoMenu, scanner);
            //----------------------------------
            if(opcaoMenu==10)
                break;

            //------- RECEBER CHAVE E VALOR
            System.out.println("--------------------");
            System.out.println("Digite a chave:");
            chave = scanner.nextBigInteger();

            if (opcaoMenu == OpcoesMenu.CREATE.getValor() || opcaoMenu == OpcoesMenu.UPDATE.getValor()) {
                System.out.println("Digite o valor ");
                valor = scanner.next();
            }
            //----------------------------------

            //------- CRIACAO DE THREADS
            Thread t = new ThreadProcess(chave, valor, opcaoMenu);
            t.start();
            //----------------------------------
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
