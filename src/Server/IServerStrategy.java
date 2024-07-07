package Server;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * This interface defines a strategy for server-side processing.
 */
public interface IServerStrategy {

    /**
     * Defines the strategy to be implemented by the server to handle input and output streams.
     *
     * @param inFromClient the input stream from the client
     * @param outputStream the output stream to the client
     */
    void serverStrategy(InputStream inFromClient, OutputStream outputStream);
}
