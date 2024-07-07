package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.EmptyMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.SimpleMazeGenerator;

import java.io.*;

/**
 * ServerStrategyGenerateMaze implements IServerStrategy to generate a maze
 * based on client input and send it back compressed.
 */
public class ServerStrategyGenerateMaze implements IServerStrategy {
    private Configurations config = Configurations.getInstance();

    /**
     * Implements the strategy to generate a maze based on the size received from the client,
     * compress it, and send it back to the client.
     *
     * @param inFromClient the input stream from the client
     * @param outputStream the output stream to the client
     */
    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outputStream) {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outputStream);

            ByteArrayOutputStream byteArrayMaze = new ByteArrayOutputStream();
            int[] mazeSize = (int[]) fromClient.readObject();
            int numOfRows = mazeSize[0];
            int numOfCols = mazeSize[1];
            Maze maze = generateChoice(config.getGeneratingName(), numOfRows, numOfCols);

            MyCompressorOutputStream compressorOutputStream = new MyCompressorOutputStream(byteArrayMaze);
            compressorOutputStream.write(maze.toByteArray());
            compressorOutputStream.flush();
            toClient.writeObject(byteArrayMaze.toByteArray());
            toClient.flush();
            fromClient.close();
            toClient.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates a maze based on the provided choice of maze generator.
     *
     * @param choice the name of the maze generator to use
     * @param row the number of rows in the maze
     * @param col the number of columns in the maze
     * @return the generated Maze
     */
    private Maze generateChoice(String choice, int row, int col) {
        return switch (choice) {
            case "EmptyMazeGenerator" -> new EmptyMazeGenerator().generate(row, col);
            case "MyMazeGenerator" -> new MyMazeGenerator().generate(row, col);
            case "SimpleMazeGenerator" -> new SimpleMazeGenerator().generate(row, col);
            default -> null;
        };
    }
}
