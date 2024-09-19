package drone.sensores;


import drone.utils.GeradorDeDados;
import drone.utils.MulticastUtil;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DroneNorte extends DroneBase {

    public DroneNorte(String servidorIp, int port) {
        super(servidorIp, port, "Drone Norte");
    }

    @Override
    public void run() {
        ScheduledExecutorService scheduler =
                Executors.newScheduledThreadPool(1);

        // Tarefa de coleta de dados (usando GeradorDeDados)
        Runnable coletaDeDados = () -> {
            String dados = GeradorDeDados.gerarDadosNorte();
            System.out.println(nome + " enviando: " + dados);

            try {
                MulticastUtil.enviarDados(dados, servidorIp, port);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        // Agendamento da tarefa de coleta de dados com taxa fixa (fixed-rate)
        scheduler.scheduleAtFixedRate(
                coletaDeDados,
                0,
                3,
                TimeUnit.SECONDS);

        // pra desligar:
        scheduler.schedule(()-> {
            scheduler.shutdown();
            System.out.println("Encerrando o serviço de " +
                    "monitoramento de dados do Norte.");
        }, 30, TimeUnit.SECONDS);

    }
}
