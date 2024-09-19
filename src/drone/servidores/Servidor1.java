package drone.servidores;

import drone.utils.MulticastUtil;

import java.io.IOException;
import java.net.MulticastSocket;


public class    Servidor1 extends ServidorBase {

    public Servidor1(String multicastIp, int port) {
        super(multicastIp, port, "Servidor 1");
    }

    @Override
    public void run() {
        try {
            MulticastSocket socket = MulticastUtil.criarMulticastSocket(multicastIp, port);
            while (true) {
                String dados = MulticastUtil.receberDados(socket);
                System.out.println(nome + " recebeu: " + dados);
                MulticastUtil.enviarDados(dados, multicastIp, port);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}