package sensores;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.DatagramSocket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DroneNorte {
    private final int servidorPort = 44441;

    public void enviarDados() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        try {
            DatagramSocket droneSocket = new DatagramSocket();
            InetAddress servidorAddress = InetAddress.getLocalHost();

            System.out.println("Drone Norte enviando dados ao servidor 1 na porta " + servidorPort);

            scheduler.scheduleAtFixedRate(() -> {
                try {
                    String dados = "Temp: " + (30 + (int) (Math.random() * 10)) + "°C, Umidade: " + (60 + (int) (Math.random() * 20)) + "%";
                    byte[] buffer = dados.getBytes();

                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, servidorAddress, servidorPort);
                    droneSocket.send(packet);
                    System.out.println("Drone Norte enviou ao servidor: " + dados);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, 0, 3, TimeUnit.SECONDS);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DroneNorte drone = new DroneNorte();
        drone.enviarDados();
    }
}
