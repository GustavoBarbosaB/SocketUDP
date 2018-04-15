package application;

import application.configuration.ApplicationProperties;
import application.factory.Operacao;

import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.util.Scanner;
import java.util.logging.Logger;

public class ClientMain {

    private static String port = ApplicationProperties.getInstance().
            loadProperties().getProperty("server.port");
    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static String IPADDRESS = "localhost";

    public static void main(String args[]) {
        Operacao operacao;
        DatagramSocket clientSocket = null;
        Scanner scanner = new Scanner(System.in);
/*
        try {
            clientSocket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName(IPADDRESS);
*/
            //------- Atributos a serem enviados
            Integer opcao;
            BigInteger chave;
            String valor = null;
            //----------------------------------


            //TODO criar um enum
            logger.info("Digite o comando a executar:" +
                            "\n [0] LER" +
                            "\n [1] ESCREVER" +
                            "\n [2] ATUALIZAR" +
                            "\n [3] DELETAR");
            do {
                opcao = scanner.nextInt();
            }while (opcao > 3 || opcao < 0);

            logger.info("Digite o valor da chave:");
            chave = scanner.nextBigInteger();

            if(opcao==1||opcao==2)
                valor = scanner.next();

            Operacao op = new Operacao(chave,valor==null?"":valor,opcao);

            logger.info("Total: "+op.convertData().length);









/*



            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, Integer.parseInt(port));
            clientSocket.send(sendPacket);
            clientSocket.close();

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            clientSocket.close();
        }

        */


    }




}
