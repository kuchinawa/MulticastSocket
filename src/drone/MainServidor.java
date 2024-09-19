package drone;

import drone.servidores.Servidor1;

public class MainServidor {
    public static void main(String[] args) {
        Servidor1 servidor1 = new Servidor1("224.0.0.1", 8888);

        servidor1.run();
    }
}
