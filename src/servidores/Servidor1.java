package servidores;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Servidor1 {
    private final int porta = 44441;
    private final String grupoMulticast = "224.0.0.1";
    private final int portaMulticast = 44443;

    public void iniciarServidor() {
        try {
            DatagramSocket servidorSocket = new DatagramSocket(porta);
            System.out.println("Servidor 1 (Norte) ouvindo na porta " + porta);

            byte[] buffer = new byte[1024];

            DatagramSocket multicastSocket = new DatagramSocket();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            while (true) {

                servidorSocket.receive(packet);

                String dadosRecebidos = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Servidor 1 recebeu do Drone Norte: " + dadosRecebidos);


                InetAddress grupo = InetAddress.getByName(grupoMulticast);
                DatagramPacket multicastPacket = new DatagramPacket(dadosRecebidos.getBytes(), dadosRecebidos.length(), grupo, portaMulticast);
                multicastSocket.send(multicastPacket);
//                multicastSocket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Servidor1 servidor = new Servidor1();
        servidor.iniciarServidor();
    }
}
