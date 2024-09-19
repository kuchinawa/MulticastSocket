package drone.servidores;

public abstract class ServidorBase implements Runnable {
    protected String multicastIp;
    protected int port;
    protected String nome;

    public ServidorBase(String multicastIp, int port, String nome) {
        this.multicastIp = multicastIp;
        this.port = port;
        this.nome = nome;
    }

    @Override
    public  void run(){

    };
}