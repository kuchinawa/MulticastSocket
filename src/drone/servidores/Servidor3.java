package drone.servidores;

import drone.utils.MulticastUtil;

import java.io.IOException;
import java.net.MulticastSocket;

public class Servidor3 extends ServidorBase {

    public Servidor3(String multicastIp, int port) {
        super(multicastIp, port, "Servidor 3");
    }

    @Override
    public void run() {
        try {
            MulticastSocket socket = MulticastUtil.criarMulticastSocket(multicastIp, port);
            while (true) {
                String dados = MulticastUtil.receberDados(socket);
                System.out.println(nome + " redistribuindo: " + dados);



                MulticastUtil.enviarDados(dados, multicastIp, port);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}