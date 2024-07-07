package Server;
import algorithms.mazeGenerators.Maze;
import algorithms.search.*;
import java.io.*;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.stream.Stream;

/**
 * The ServerStrategySolveSearchProblem class implements the IServerStrategy interface
 * to handle solving maze search problems for clients.
 */
public class ServerStrategySolveSearchProblem implements IServerStrategy {
    private Configurations config = Configurations.getInstance(); // Retrieve the configuration instance
    private static final String TEMP_DIR_PATH = System.getProperty("java.io.tmpdir"); // Get the temporary directory path

    /**
     * Handles the client request to solve a maze search problem.
     *
     * @param inFromClient the input stream from the client
     * @param outToClient the output stream to the client
     */
    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient); // Create an ObjectInputStream from the client's input stream
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient); // Create an ObjectOutputStream for the client's output stream

            // Read the maze from the client
            Maze maze = (Maze) fromClient.readObject();

            // Check if a solution already exists for the maze
            Solution solution = retrieveSolutionFromDirectory(maze);
            if (solution == null) {
                // Convert the maze to a searchable maze
                SearchableMaze searchableMaze = new SearchableMaze(maze);
                // Solve the maze using the specified algorithm
                solution = AlgorithmChoice(config.getSolAlgorithm(), searchableMaze);
                // Save the solution to the directory
                saveSolutionToDirectory(maze, solution);
            }

            // Send the solution to the client
            toClient.writeObject(solution);
            toClient.flush();
            fromClient.close();
            toClient.close();
        } catch (Exception e) {
            e.printStackTrace(); // Print the stack trace if an exception occurs
        }
    }

    /**
     * Chooses and applies the maze solving algorithm.
     *
     * @param choice the algorithm choice as a string
     * @param maze the searchable maze
     * @return the solution of the maze
     */
    private Solution AlgorithmChoice(String choice, SearchableMaze maze) {
        switch (choice) {
            case "BestFirstSearch":
                BestFirstSearch best = new BestFirstSearch();
                return best.solve(maze);
            case "BreadthFirstSearch":
                BreadthFirstSearch bfs = new BreadthFirstSearch();
                return bfs.solve(maze);
            case "DepthFirstSearch":
                DepthFirstSearch dfs = new DepthFirstSearch();
                return dfs.solve(maze);
            default:
                return null;
        }
    }

    /**
     * Retrieves a solution from the directory if it exists.
     *
     * @param maze the maze
     * @return the solution of the maze or null if not found
     * @throws NoSuchAlgorithmException if the hashing algorithm is not found
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if the solution class is not found
     */
    private Solution retrieveSolutionFromDirectory(Maze maze) throws NoSuchAlgorithmException, IOException, ClassNotFoundException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256"); // Get the SHA-256 message digest
        messageDigest.update(maze.toByteArray()); // Update the message digest with the maze's byte array
        byte[] mazeHash = messageDigest.digest(); // Compute the hash of the maze
        String fileName = bytesToHex(mazeHash) + ".ser"; // Convert the hash to a hex string and create a filename
        Path filePath = Paths.get(TEMP_DIR_PATH, fileName); // Create the file path in the temporary directory
        if (Files.exists(filePath)) { // Check if the file exists
            try (ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(filePath))) {
                return (Solution) objectInputStream.readObject(); // Read and return the solution from the file
            }
        }
        return null; // Return null if the file does not exist
    }

    /**
     * Saves the solution to the directory.
     *
     * @param maze the maze
     * @param solution the solution of the maze
     * @throws NoSuchAlgorithmException if the hashing algorithm is not found
     * @throws IOException if an I/O error occurs
     */
    private void saveSolutionToDirectory(Maze maze, Solution solution) throws NoSuchAlgorithmException, IOException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256"); // Get the SHA-256 message digest
        messageDigest.update(maze.toByteArray()); // Update the message digest with the maze's byte array
        byte[] mazeHash = messageDigest.digest(); // Compute the hash of the maze
        String fileName = bytesToHex(mazeHash) + ".ser"; // Convert the hash to a hex string and create a filename
        Path filePath = Paths.get(TEMP_DIR_PATH, fileName); // Create the file path in the temporary directory
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(filePath))) {
            objectOutputStream.writeObject(solution); // Write the solution to the file
        }
    }

    private static final char[] hexArray = "0123456789ABCDEF".toCharArray(); // Hexadecimal characters array

    /**
     * Converts a byte array to a hexadecimal string.
     *
     * @param bytes the byte array
     * @return the hexadecimal string representation of the byte array
     */
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2]; // Create a char array to hold the hex characters
        for (int j = 0; j < bytes.length; j++) { // Iterate over the byte array
            int v = bytes[j] & 0xFF; // Get the unsigned value of the byte
            hexChars[j * 2] = hexArray[v >>> 4]; // Get the high nibble
            hexChars[j * 2 + 1] = hexArray[v & 0x0F]; // Get the low nibble
        }
        return new String(hexChars); // Convert the char array to a string and return it
    }


}

