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

        byte[] receivedData = new byte[1480];
        String resposta = "";

        try {
            clientSocket = new DatagramSocket(5003);
            InetAddress IPAddress = InetAddress.getByName(IPADDRESS);
            DatagramPacket sendPacket = new DatagramPacket(dados, dados.length, IPAddress, Integer.parseInt(port));
            System.out.println("enviado: " + dados);
            System.out.println("tamanho enviado: " + dados.length);

            clientSocket.send(sendPacket);

            // MENSAGEM VINDA DO SERVIDOR
            DatagramPacket receivedPacket = new DatagramPacket(receivedData, receivedData.length);
            clientSocket.receive(receivedPacket);
            String data = new String(receivedPacket.getData(), receivedPacket.getOffset(), receivedPacket.getLength());

            System.out.println(data);


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
