package drone.sensores;

public abstract class DroneBase implements Runnable {
    protected String servidorIp;
    protected int port;
    protected String nome;

    public DroneBase(String servidorIp, int port, String nome) {
        this.servidorIp = servidorIp;
        this.port = port;
        this.nome = nome;
    }

    @Override
    public abstract void run();
}