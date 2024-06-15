
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatCliente implements Runnable {

   //private String servidorEdrss = "127.0.0.1"; // IP da máquina
    private ClienteSocket clienteSocket;
    Scanner scanner = new Scanner(System.in);

    public void start() throws IOException {
        try{
        System.out.println("Digite o ip do servidor que deseja conectar:");
        String servidorEdrss = scanner.nextLine();
        clienteSocket = new ClienteSocket(new Socket(servidorEdrss, 8080));
        System.out.println("Cliente conectado ao servidor em " + servidorEdrss);
        System.out.println("Digite seu nome de usuário:");
        String nomeUsuario = scanner.nextLine();
        clienteSocket.sendMsg(nomeUsuario);

        //instruções
        System.out.println("Como usar o chat:");
        System.out.println("comando 'Lista' -> imprime nome dos usuários");
        System.out.println("enviar a mensagem -> digitar o usuário e mensagem separados por ';'");
        System.out.println("Ex  -> 'nome;mensagem' ");

        new Thread(this).start();
        messageLoop();
        } finally{
            clienteSocket.close();
        }
    }

    @Override
    public void run(){
        String msg;
        while ((msg = clienteSocket.getMessage()) != null) {
            System.out.println(msg);
        }
    }

    // Método que mantém o código do Cliente rodando até ser digitado a palavra sair
    private void messageLoop() throws IOException {
        String msg;
        do {
            System.out.println("Digite uma mensagem (Para sair digite: Sair)");
            msg = scanner.nextLine();
            clienteSocket.sendMsg(msg);
           
        } while (!msg.equalsIgnoreCase("sair"));
        
    }

    public static void main(String[] args) {
        try {
            ChatCliente chatCliente = new ChatCliente();
            chatCliente.start();
        } catch (IOException ex) {
            System.out.println("Erro ao iniciar cliente " + ex.getMessage());
        }
    }
}
