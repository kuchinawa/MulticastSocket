package servidores;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

public class ServidorMulticast {
    private final String grupoMulticast = "224.0.0.1";
    private final int portaMulticast = 44443;
    private String grupoMulticast2 = "224.0.0.2";
    private int portaMulticast2 = 44447;
    private final String caminhoLog = "log_dados_recebidos.txt";

    public void escutarGrupo(String nomeServidor, String grupoMulticast2, int portaMulticast2) {
        this.grupoMulticast2 = grupoMulticast2;
        this.portaMulticast2 = portaMulticast2;

        try {
            MulticastSocket multicastSocket = new MulticastSocket(portaMulticast);
            InetAddress grupo = InetAddress.getByName(grupoMulticast);
            multicastSocket.joinGroup(grupo);

            MulticastSocket multicastSocket2 = new MulticastSocket(portaMulticast2);
            InetAddress grupo2 = InetAddress.getByName(grupoMulticast2);
            multicastSocket2.joinGroup(grupo2);

            System.out.println("Grupo: " + grupo);
            System.out.println(nomeServidor + " multicast ouvindo no grupo " + grupoMulticast);

            BlockingQueue<String> filaDeMensagens = new java.util.concurrent.LinkedBlockingQueue<>();

            ExecutorService executorService = java.util.concurrent.Executors.newFixedThreadPool(2);
            Runnable tarefaCaptar = () -> {
                while (true) {
                    byte[] buffer = new byte[1024];
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    try {
                        multicastSocket.receive(packet);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    String dadosRecebidos = new String(packet.getData(), 0, packet.getLength());
                    System.out.println("Servidor multicast recebeu: " + dadosRecebidos);

                    filaDeMensagens.add(dadosRecebidos);
                    registrarDadosNoLog(dadosRecebidos);
                }
            };

            Runnable tarefaRepassar = () -> {
                while (true) {
                    if (filaDeMensagens.peek() != null) {
                        String dados = filaDeMensagens.poll();
                        byte[] buffer = dados.getBytes();
                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, grupo2, portaMulticast2);
                        try {
                            multicastSocket2.send(packet);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            };

            executorService.submit(tarefaCaptar);
            executorService.submit(tarefaRepassar);

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
//        ServidorMulticast servidor3 = new ServidorMulticast();
//        servidor3.escutarGrupo("Servidor3", "224.0.0.2", 44447);
        ServidorMulticast servidor4 = new ServidorMulticast();
        servidor4.escutarGrupo("Servidor4", "224.0.0.3", 44448);
    }
}
