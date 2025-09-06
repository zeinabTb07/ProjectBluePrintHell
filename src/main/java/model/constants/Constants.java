package model.constants;
import model.Level;

import java.awt.*;
import java.util.ArrayList;

public class Constants {

    public static final double SCALE = 1;

    public static final int FRAME_WIDTH = (int)(1000 * SCALE);
    public static final int FRAME_HEIGHT = (int)(700 * SCALE);

    public static final int SYSTEMS_WIDTH = (int)(90 * SCALE);
    public static final int PORT_GAP = (int)(40 * SCALE);
    public static final int INDUCTOR_HEIGHT = (int)(20 * SCALE);
    public static final int PORT_SIZE = (int)(8 * SCALE);
    public static final int PACKET_SIZE_SCALE = (int)(5 * SCALE);

    public static final double WAVE_SPEED = 2 * SCALE;
    public static final double MAX_WAVE_R = 400 * SCALE;
    public static double PACKET_SPEED = 20 * SCALE;
    public static double PACKET_ACCELERATION = 10 * SCALE;

    public static Stroke LINE_STROKE = new BasicStroke((float)(3 * SCALE));

    public static ArrayList<Level> levels = new ArrayList<>();

    public class Colors {
        public static final Color SYSTEM = new Color(0x4FC3F7);
        public static final Color INPUT_PORT = new Color(156, 39, 176);
        public static final Color OUTPUT_PORT =  new Color(255, 87, 34);
        public static final Color PACKET = new Color(0, 255, 191);
        public static final Color LINE = new Color(0x90A4AE);
        public static final Color CONNECTION = new Color(0xBA68C8);
        public static final Color HP_POINT = new Color(58, 100, 250);
        public static final Color STATUS_CONNECTED = new Color(0x66BB6A);
        public static final Color STATUS_DISCONNECTED = new Color(0xFBC02D);
        public static final Color TROJAN_PACKET = new Color(153, 204, 0);
    }
}

