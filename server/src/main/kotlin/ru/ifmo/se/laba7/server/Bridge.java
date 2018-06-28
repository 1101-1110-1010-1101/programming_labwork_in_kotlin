package ru.ifmo.se.laba7.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.Exchanger;

public class Bridge {
    public Exchanger<String> ex = new Exchanger<String>();
    private DatagramSocket socket;
    private boolean running;
    private byte[] buf = new byte[64000];

    public Bridge() throws SocketException { socket = new DatagramSocket(15000); }

    public void run() throws IOException, InterruptedException {
        running = true;
        while (running) {
            DatagramPacket packet
                    = new DatagramPacket(buf, buf.length);
            socket.receive(packet);

            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            packet = new DatagramPacket(buf, buf.length, address, port);
            String received
                    = new String(packet.getData(), 0, packet.getLength());
            System.out.println(received);
            if (received.split(" ")[0].equals("connect"))
                if (UserController.Companion.userLogin(received.split(" ")[1], received.split(" ")[2]))
                    packet.setData("good".getBytes());
                else packet.setData("sorry".getBytes());
            else if (received.split(" ")[0].equals("reg")) {
                UserController.Companion.registerUser(received.split(" ")[1], received.split(" ")[2]);
                packet.setData("okay".getBytes());
            }
            else packet.setData(Connector.Companion.getM().getBytes());
            socket.send(packet);
        }
        socket.close();
    }
}
