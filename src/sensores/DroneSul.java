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
            InetAddress servidorAddress = InetAddress.getByName("192.168.137.1");

            System.out.println("Drone Sul enviando dados ao servidor 2 na porta " + servidorPort);

            scheduler.scheduleAtFixedRate(() -> {
                try {
                    String dados = "Temp: " + (10 + (int) (Math.random() * 10)) + "°C, " +
                                    "Umidade: " + (40 + (int) (Math.random() * 20)) + "%, " +
                                     "Pressão Atmosférica: " + (950 + (int) (Math.random() * 50)) + " hPa, " +
                                    "Radiação Solar: " + (500 + (int) (Math.random() * 200)) + " W/m²";
                    /*
                    Temperatura (Sul): 10°C a 20°C.
                    Umidade (Sul): 40% a 60%.
                    Pressão Atmosférica (Sul): 950 hPa a 1000 hPa.
                    Radiação Solar (Sul): 500 W/m² a 700 W/m².
                     */
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

