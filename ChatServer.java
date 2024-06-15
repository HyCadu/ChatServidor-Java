import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatServer {
    public static int portaServidor = 8080;
    private ServerSocket serverSocket;
    private final List<Usuarios> usuarios = Collections.synchronizedList(new ArrayList<>());

    // Método para iniciar o servidor
    public void start() throws IOException {
        InetAddress inetAddress = InetAddress.getLocalHost();
        String ip = inetAddress.getHostAddress();
        System.out.println("Endereço IP do servidor: " + ip);

        serverSocket = new ServerSocket(portaServidor);
        System.out.println("Servidor iniciou na porta " + portaServidor);
        clienteConectionLoop();
    }

    
    private void clienteConectionLoop() throws IOException {
        while (true) {
            ClienteSocket clienteSocket = new ClienteSocket(serverSocket.accept());
            String nomeUsuario = clienteSocket.getMessage();
            Usuarios usuario = new Usuarios(clienteSocket, nomeUsuario);
            usuarios.add(usuario);

            // Cria uma nova thread para lidar com a conexão do cliente
            new Thread(() -> clientMessageLoop(usuario)).start();
        }
    }
    
    //loop de troca de mensagens
    public void clientMessageLoop(Usuarios usuario) {
        String msg;
        try {
            while ((msg = usuario.getEndereco().getMessage()) != null) {
                if (msg.equalsIgnoreCase("Sair")) {
                    break;
                }
                else if(msg.equalsIgnoreCase("lista")){
                    sendMessagetoMe(usuario, "----Lista De Usuários----");
                    synchronized (usuarios){
                    for(Usuarios usuarios : usuarios){
                        sendMessagetoMe(usuario, usuarios.getNome().toString());
                    }
                }
                }
                 else {
                    sendMessagetoOne(usuario,msg);
                }
            }
        } finally {
            usuario.getEndereco().close();
            usuarios.remove(usuario);
        }
    }
    //envia mensagem para todos(sem uso específico no trabalho)
    private void sendMessagetoAll(Usuarios emissor, String msg) {
        synchronized (usuarios) {
            for (Usuarios usuario : usuarios) {
            usuario.getEndereco().sendMsg(msg); 
            }
        }
    }
    //envia a mensagem somente a quem solicitou
    private void sendMessagetoMe(Usuarios emissor, String msg) {
        synchronized (usuarios) {
            for (Usuarios usuario : usuarios) {
            if(emissor.equals(usuario)){
                usuario.getEndereco().sendMsg(msg); 
            }
            }
        }
    }
    //envia mensagem apenas para o selecionado
    private void sendMessagetoOne(Usuarios emissor, String msg){
        String[] mensagemPonto = msg.split(";");
        synchronized (usuarios) {
            for (Usuarios usuario : usuarios) {
                if (mensagemPonto[0].equalsIgnoreCase(usuario.getNome())) {
                    usuario.getEndereco().sendMsg("Mensagem enviada de "+emissor.getNome()+":"+mensagemPonto[1]);
                }
            }
        }

    }

    // Método principal para iniciar o servidor
    public static void main(String[] args) {
        try {
            ChatServer server = new ChatServer();
            server.start();
        } catch (IOException ex) {
            System.out.println("Erro ao iniciar o servidor: " + ex.getMessage());
        }
    }
}