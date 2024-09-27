package sensores;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.DatagramSocket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DroneSul {
    private final int servidorPort = 44442;

    public void enviarDados() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        try {
            DatagramSocket droneSocket = new DatagramSocket();
            InetAddress servidorAddress = InetAddress.getLocalHost();

            System.out.println("Drone Sul enviando dados ao servidor 2 na porta " + servidorPort);

            scheduler.scheduleAtFixedRate(() -> {
                try {
                    String dados = "Temp: " + (10 + (int) (Math.random() * 10)) + "°C, Umidade: " + (40 + (int) (Math.random() * 20)) + "%";
                    byte[] buffer = dados.getBytes();

                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, servidorAddress, servidorPort);
                    droneSocket.send(packet);
                    System.out.println("Drone Sul enviou ao servidor: " + dados);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, 0, 3, TimeUnit.SECONDS);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DroneSul drone = new DroneSul();
        drone.enviarDados();
    }
}

