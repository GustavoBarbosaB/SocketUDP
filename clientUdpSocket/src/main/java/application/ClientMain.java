package application;

import application.configuration.ApplicationProperties;
import application.model.Operacao;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Logger;

public class ClientMain {

    static String port;
    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void main(String args[]) {

        port = ApplicationProperties.getInstance().loadProperties().getProperty("server.port");
        String asad = "bumbum de elefante";

        DatagramSocket clientSocket = null;
        try {
            clientSocket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName("localhost");

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(bos);

            byte[] sizeChave = asad.getBytes("UTF-16");
            dos.write(sizeChave.length-2);
            dos.writeChars(asad);

            dos.flush();


            byte[] sendData = bos.toByteArray();

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


    }




}
