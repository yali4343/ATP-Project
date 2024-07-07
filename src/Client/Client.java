package Client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * The Client class represents a client that connects to a server using a specified IP address,
 * port, and communication strategy.
 */
public class Client {
    private InetAddress serverIP;
    private int serverPort;
    private IClientStrategy strategy;

    /**
     * Constructs a Client with the specified server IP address, port, and strategy.
     *
     * @param serverIP the IP address of the server
     * @param serverPort the port number of the server
     * @param strategy the communication strategy to use
     */
    public Client(InetAddress serverIP, int serverPort, IClientStrategy strategy) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.strategy = strategy;
    }

    /**
     * Communicates with the server using the specified strategy.
     * Establishes a socket connection to the server, and then delegates the communication
     * to the strategy's clientStrategy method.
     */
    public void communicateWithServer(){
        try(Socket serverSocket = new Socket(serverIP, serverPort)){
            System.out.println("connected to server - IP = " + serverIP + ", Port = " + serverPort);
            strategy.clientStrategy(serverSocket.getInputStream(), serverSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
