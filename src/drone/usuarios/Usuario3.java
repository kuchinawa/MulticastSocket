package drone.usuarios;

public class Usuario3 extends UsuarioBase {

    public Usuario3(String multicastIp, int port) {
        super(multicastIp, port, "Usuário 3");
    }

    @Override
    protected void processReceivedData(String data) {
        System.out.println("Usuário 3 processando dados: " + data);
    }
}