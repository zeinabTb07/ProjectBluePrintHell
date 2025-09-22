package shared.utils;

import com.google.gson.Gson;
import shared.api.service.mapper.Records;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

public class PlayerQueue {
    private final Queue<Records.PlayerRecord> queue = new LinkedList<>();
    private final File file;
    private final String path = "src/main/resources/client/record.json";
    private final Gson gson = new Gson();

    public PlayerQueue() {
        this.file = new File(path);
        loadFromFile();
    }

    public void add(Records.PlayerRecord record) {
        queue.add(record);
        appendToFile(record);
    }

    public Records.PlayerRecord poll() {
        if (queue.isEmpty()) return null;
        Records.PlayerRecord record = queue.poll();
        rewriteFile();
        return record;
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public int size() {
        return queue.size();
    }

    private void appendToFile(Records.PlayerRecord record) {
        try (FileWriter fw = new FileWriter(file, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            String json = gson.toJson(record);
            bw.write(json);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void rewriteFile() {
        try (FileWriter fw = new FileWriter(file);
             BufferedWriter bw = new BufferedWriter(fw)) {
            for (Records.PlayerRecord record : queue) {
                String json = gson.toJson(record);
                bw.write(json);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFromFile() {
        if (!file.exists()) return;
        try (FileReader fr = new FileReader(file);
             BufferedReader br = new BufferedReader(fr)) {
            String line;
            while ((line = br.readLine()) != null) {
                Records.PlayerRecord record = gson.fromJson(line, Records.PlayerRecord.class);
                queue.add(record);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

