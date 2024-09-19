package drone.usuarios;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public abstract class UsuarioBase {
    protected String multicastIp;
    protected int port;
    protected String nome;

    public UsuarioBase(String multicastIp, int port, String nome) {
        this.multicastIp = multicastIp;
        this.port = port;
        this.nome = nome;
    }

    public void start() throws IOException {
        MulticastSocket socket = new MulticastSocket(port);
        InetAddress group = InetAddress.getByName(multicastIp);
        socket.joinGroup(group);

        byte[] buffer = new byte[256];
        while (true) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);
            String receivedData = new String(packet.getData(), 0, packet.getLength());
            System.out.println(nome + " recebeu: " + receivedData);
            processReceivedData(receivedData);
        }
    }

    protected abstract void processReceivedData(String data);
}

