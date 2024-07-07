package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The Server class represents a server that listens for client connections and
 * handles them using a specified strategy.
 */
public class Server {
    private int port; // Port number on which the server listens for connections
    private int listeningIntervalMS; // Interval in milliseconds for socket timeout
    private IServerStrategy strategy; // Strategy for handling client connections
    private volatile boolean stop; // Flag to stop the server
    private Configurations config = Configurations.getInstance(); // Configuration instance
    private ExecutorService threadPool; // Thread pool for handling client connections

    /**
     * Constructs a Server instance with the specified port, listening interval, and strategy.
     *
     * @param port the port number on which the server listens
     * @param listeningIntervalMS the interval in milliseconds for socket timeout
     * @param strategy the strategy for handling client connections
     */
    public Server(int port, int listeningIntervalMS, IServerStrategy strategy) {
        this.port = port;
        this.listeningIntervalMS = listeningIntervalMS;
        this.strategy = strategy;
        this.threadPool = Executors.newFixedThreadPool(config.getNumOfThreads()); // Initialize thread pool with configured number of threads
    }

    /**
     * Starts the server and listens for client connections. This method runs in a loop until the server is stopped.
     */
    public void startThread() {
        try {
            ServerSocket serverSocket = new ServerSocket(port); // Create a server socket
            serverSocket.setSoTimeout(listeningIntervalMS); // Set socket timeout

            while (!stop) { // Continue to listen until stop flag is set
                try {
                    Socket clientSocket = serverSocket.accept(); // Accept a client connection

                    // Insert the new task into the thread pool:
                    threadPool.submit(() -> {
                        applyStrategy(clientSocket); // Handle the client connection using the strategy
                    });
                } catch (SocketTimeoutException e){
                }
            }
            serverSocket.close(); // Close the server socket
            threadPool.shutdown(); // Shutdown the thread pool
        } catch (IOException e) {
            System.out.println("IOException");

        }
    }

    /**
     * Starts the server in a new thread.
     */
    public void start() {
        Thread thread = new Thread(this::startThread); // Create a new thread to run the server
        thread.start(); // Start the server thread
    }

    /**
     * Applies the strategy to handle a client connection.
     *
     * @param clientSocket the socket representing the client connection
     */
    private void applyStrategy(Socket clientSocket) {
        try {
            strategy.serverStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream()); // Execute the strategy for handling client input and output
            clientSocket.close(); // Close the client socket
        } catch (IOException e){
            System.out.println("IOException");
        }
    }

    /**
     * Stops the server.
     */
    public void stop() {
        stop = true; // Set the stop flag to true
    }
}
