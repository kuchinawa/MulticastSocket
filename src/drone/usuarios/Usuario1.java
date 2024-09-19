package drone.usuarios;

public class Usuario1 extends UsuarioBase {

    public Usuario1(String multicastIp, int port) {
        super(multicastIp, port, "Usuário 1");
    }

    @Override
    protected void processReceivedData(String data) {
        System.out.println("Usuário 1 processando dados: " + data);
    }
}