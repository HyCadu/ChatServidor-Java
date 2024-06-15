import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

public class ClienteSocket {
    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;

    public ClienteSocket(Socket socket) throws IOException{
        this.socket = socket;
        System.out.println("Cliente " + socket.getRemoteSocketAddress() + " conectou");
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
    }
    public SocketAddress getRemoteSocketAddress(){
       return socket.getRemoteSocketAddress();
    }
    
    public void close(){
        try {
            in.close();
            out.close();
            socket.close();

        } catch (Exception e) {
            System.out.println("Mensagem de erro");
        }
    }

    public String getMessage(){
        try {
            return in.readLine();
        } catch (Exception e) {
            return null;
        }
    }
    public boolean sendMsg(String msg){
        out.println(msg);
        return !out.checkError();
    }

}

