package Client;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * The IClientStrategy interface represents a strategy for client-side communication with a server.
 * Implementing classes will define the specific behavior for handling input and output streams
 * during communication with the server.
 */
public interface IClientStrategy {
    /**
     * Defines the strategy for client-side communication with a server.
     * This method will be called to handle the input and output streams for communication.
     *
     * @param inFromServer the input stream from the server
     * @param outToServer the output stream to the server
     */
    void clientStrategy(InputStream inFromServer, OutputStream outToServer);
}
