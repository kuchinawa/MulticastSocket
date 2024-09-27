package servidores;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Servidor2 {
    private final int porta = 44442;
    private final String grupoMulticast = "224.0.0.1";
    private final int portaMulticast = 44443;

    public void iniciarServidor() {
        try {
            DatagramSocket servidorSocket = new DatagramSocket(porta);
            System.out.println("Servidor 2 (Sul) ouvindo na porta " + porta);

            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            DatagramSocket multicastSocket = new DatagramSocket();
            while (true) {

                servidorSocket.receive(packet);

                String dadosRecebidos = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Servidor 2 recebeu do Drone Sul: " + dadosRecebidos);



                InetAddress grupo = InetAddress.getByName(grupoMulticast);
                DatagramPacket multicastPacket = new DatagramPacket(dadosRecebidos.getBytes(), dadosRecebidos.length(), grupo, portaMulticast);
                multicastSocket.send(multicastPacket);
            }
//            multicastSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Servidor2 servidor = new Servidor2();
        servidor.iniciarServidor();
    }
}
