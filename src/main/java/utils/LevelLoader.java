package utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class LevelLoader {
    private static final Logger logger = LoggerFactory.getLogger(LevelLoader.class);
    private static final Gson GSON = new Gson();
    private static String filePath = "src/main/resources/levels.json";

    public static ArrayList<Level> loadLevels(){
        return loadLevels(filePath);
    }

    public static ArrayList<Level> loadLevels(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            Type levelListType = new TypeToken<ArrayList<Level>>() {}.getType();
            logger.info("Levels loaded correctly ");
            return GSON.fromJson(reader, levelListType);
        } catch (IOException e) {
            logger.error("Failed to load levels from :{}",filePath);
            throw new RuntimeException();
        }
    }
}