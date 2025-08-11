import controller.GameController;
import events.EventBus;
import controller.FrameManager;
import events.UIEvents.*;

public class Main {
    public static void main(String[] args) {
        new GameController().run();
    }
}



