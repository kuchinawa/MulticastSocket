package drone.usuarios;

public class Usuario2 extends UsuarioBase {

    public Usuario2(String multicastIp, int port) {
        super(multicastIp, port, "Usuário 2");
    }

    @Override
    protected void processReceivedData(String data) {
        System.out.println("Usuário 2 processando dados: " + data);
    }
}