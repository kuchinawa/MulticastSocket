package drone.utils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastUtil {

    public static MulticastSocket criarMulticastSocket(String ip, int port) throws IOException {
        MulticastSocket Msocket = new MulticastSocket(port);
        InetAddress group = InetAddress.getByName(ip);
        Msocket.joinGroup(group);
        return Msocket;
    }

    public static void enviarDados(String data, String multicastIp, int port) throws IOException {
        MulticastSocket socket = new MulticastSocket();
        InetAddress group = InetAddress.getByName(multicastIp);
        byte[] buffer = data.getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, port);
        socket.send(packet);
        socket.close();
    }

    public static String receberDados(MulticastSocket socket) throws IOException {
        byte[] buffer = new byte[256];
        DatagramPacket packet = new DatagramPacket(
                buffer,
                buffer.length);
        socket.receive(packet);

        return new String(packet.getData(), 0, packet.getLength());
    }
}