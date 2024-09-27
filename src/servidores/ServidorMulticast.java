package servidores;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ServidorMulticast {
    private final String grupoMulticast = "224.0.0.1";
    private final int portaMulticast = 44443;
    private final String caminhoLog = "log_dados_recebidos.txt";

    public void escutarGrupo() {
        try {
            MulticastSocket multicastSocket = new MulticastSocket(portaMulticast);
            InetAddress grupo = InetAddress.getByName(grupoMulticast);
            multicastSocket.joinGroup(grupo);

            System.out.println("Servidor multicast ouvindo no grupo " + grupoMulticast);

            while (true) {
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                multicastSocket.receive(packet);

                String dadosRecebidos = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Servidor multicast recebeu: " + dadosRecebidos);


                registrarDadosNoLog(dadosRecebidos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void registrarDadosNoLog(String dados) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoLog, true))) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String agora = LocalDateTime.now().format(dtf);
            writer.write("[" + agora + "] Dados recebidos: " + dados);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ServidorMulticast servidor3 = new ServidorMulticast();
        servidor3.escutarGrupo();
    }
}
