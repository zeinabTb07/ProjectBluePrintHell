package model.constants;

import model.Level;

import java.awt.*;
import java.util.ArrayList;

public class Constants {
    public static final int FRAME_WIDTH = 1000;
    public static final int Frame_HEIGHT = 700;
    // مثکه توی جی فریم به طول میگن عرض! بعد به عرض میگن ارتفاع
    public static final int SYSTEMS_WIDTH = 90;
    public static final int PORT_GAP = 50;
    public static final int INDUCTOR_HEIGHT = 20;
    public static final int PORT_SIZE = 8;
    public static final int PACKET_SIZE_SCALE = 4;
    public static final double WAVE_SPEED = 2;
    public static final double MAX_WAVE_R = 400 ;
    public static double PACKET_SPEED = 15;
    public static double PACKET_ACCELERATION = 10;
    public static Stroke LINE_STROKE = new BasicStroke(3);
    public static ArrayList<Level> levels = new ArrayList<>();
    static {
        levels.add(new Level1());
        levels.add(new Level2());
    }

    public class Colors {
        public static final Color SYSTEM = new Color(0x4FC3F7);
        public static final Color INPUT_PORT = new Color(156, 39, 176);
        public static final Color OUTPUT_PORT =  new Color(255, 87, 34);
        public static final Color PACKET = new Color(0, 255, 191);
        public static final Color LINE = new Color(0x90A4AE);
        public static final Color CONNECTION = new Color(0xBA68C8);
        public static final Color STATUS_CONNECTED = new Color(0x66BB6A);
        public static final Color STATUS_DISCONNECTED = new Color(0xFBC02D);
    }

}
