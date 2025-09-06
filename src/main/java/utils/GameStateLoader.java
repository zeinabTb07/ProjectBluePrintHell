package utils;

import model.GameState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class GameStateLoader {
    private static final Logger logger = LoggerFactory.getLogger(GameStateLoader.class);
    private static final String DEFAULT_FILE_PATH = "src/main/resources/gamestate.ser";

    public void saveGameState(GameState gameState, String filePath) throws IOException {
        if (gameState == null) {
            logger.error("Cannot save null GameState");
            throw new IllegalArgumentException("GameState cannot be null");
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(gameState);
            logger.info("GameState successfully saved to {}", filePath);
        } catch (IOException e) {
            logger.error("Failed to save GameState to {}: {}", filePath, e.getMessage(), e);
            throw e;
        }
    }


    public void saveGameState(GameState gameState) throws IOException {
        saveGameState(gameState, DEFAULT_FILE_PATH);
    }

    public GameState loadGameState(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            GameState gameState = (GameState) ois.readObject();
            logger.info("GameState successfully loaded from {}", filePath);
            return gameState;
        } catch (IOException e) {
            logger.error("Failed to load GameState from {}: {}", filePath, e.getMessage(), e);
            throw e;
        } catch (ClassNotFoundException e) {
            logger.error("Class not found while loading GameState from {}: {}", filePath, e.getMessage(), e);
            throw e;
        }
    }


    public GameState loadGameState() throws IOException, ClassNotFoundException {
        return loadGameState(DEFAULT_FILE_PATH);
    }
}