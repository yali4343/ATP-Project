package Server;

import java.io.InputStream;
import java.util.Properties;

/**
 * Configurations class implements a singleton pattern to manage
 * configuration properties.
 */
public class Configurations {
    private static Configurations instance;

    /**
     * Private constructor to prevent instantiation.
     */
    private Configurations() {}

    /**
     * Returns the singleton instance of the Configurations class.
     *
     * @return the singleton instance
     */
    public static Configurations getInstance() {
        if (instance == null) {
            instance = new Configurations();
        }
        return instance;
    }

    /**
     * Retrieves the number of threads from the configuration properties.
     *
     * @return the number of threads defined in the properties file, or 0 if an error occurs
     */
    public int getNumOfThreads() {
        try (InputStream inputStream = Configurations.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties properties = new Properties();
            properties.load(inputStream);
            return Integer.parseInt(properties.getProperty("threadPoolSize"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Retrieves the maze generating algorithm name from the configuration properties.
     *
     * @return the maze generating algorithm name defined in the properties file, or null if an error occurs
     */
    public String getGeneratingName() {
        try (InputStream inputStream = Configurations.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties.getProperty("mazeGeneratingAlgorithm");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves the maze solving algorithm name from the configuration properties.
     *
     * @return the maze solving algorithm name defined in the properties file, or null if an error occurs
     */
    public String getSolAlgorithm() {
        try (InputStream inputStream = Configurations.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties.getProperty("mazeSearchingAlgorithm");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
