import events.EventBus;
import controller.FrameManager;
import events.UIEvents.*;

public class Main {
    public static void main(String[] args) {
        FrameManager frameManager = new FrameManager();

        EventBus.publish(new OpenMenuEvent());
    }
}



