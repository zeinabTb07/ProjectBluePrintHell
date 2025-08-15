import controller.GameController;
import events.EventBus;
import controller.FrameManager;
import events.UIEvents.*;
import model.constants.GeometryUtils;

import java.awt.*;
import java.awt.geom.Path2D;

public class Main {
    public static void main(String[] args) {
        new GameController().run();
    }
}



