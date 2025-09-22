package client;

import client.controller.FrameManager;
import shared.model.GameState;
import shared.model.levels.Level1;

public class Main {
    public static void main(String[] args) {
        new FrameManager(new GameState(new Level1()));
    }
}
