package ru.ifmo.se.laba7.klient;

import java.io.IOException;
import java.net.*;

public class Klient {
    private DatagramSocket socket;
    private InetAddress address;

    private byte[] buf = new byte[64000];

    public Klient() throws SocketException, UnknownHostException {
        socket = new DatagramSocket();
        address = InetAddress.getByName("localhost");
    }

    public String sendEcho(String msg) throws IOException {
        buf = msg.getBytes();
        DatagramPacket packet
                = new DatagramPacket(buf, buf.length, address, 15000);
        socket.send(packet);
        buf = new byte[64000];
        packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        System.out.println(packet.getLength());
        String received = new String(
                packet.getData(), 0, packet.getLength());
        return received;
    }

    public void close() {
        socket.close();
    }
}

