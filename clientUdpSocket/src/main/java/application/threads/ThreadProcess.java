package application.threads;

import application.configuration.ApplicationProperties;
import application.factory.Operacao;

import java.io.IOException;
import java.math.BigInteger;
import java.net.*;
import java.util.logging.Logger;

public class ThreadProcess extends Thread{

    //Todo substituir quando souber o resultado
    private Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    DatagramSocket clientSocket = null;
    private static String port = ApplicationProperties.getInstance().
            loadProperties().getProperty("server.port");
    private static String IPADDRESS = "localhost";

    final private BigInteger chave;
    final private String valor;
    final private Integer opcaoMenu;

    public ThreadProcess(BigInteger chave, String valor, Integer opcaoMenu){
        this.chave = chave;
        this.valor = valor;
        this.opcaoMenu = opcaoMenu;
    }

    @Override
    public void run(){

        Operacao operacao = new Operacao(chave, valor == null?null:valor, opcaoMenu);
        byte[] dados = operacao.convertData();

        try {
            clientSocket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName(IPADDRESS);
            DatagramPacket sendPacket = new DatagramPacket(dados, dados.length, IPAddress, Integer.parseInt(port));
            clientSocket.send(sendPacket);
            clientSocket.close();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

}
