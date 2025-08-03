package view;

import java.awt.Color;

public class GameColors {
    // 💥 نئونی و جذاب
    public static final Color ELECTRIC_BLUE = new Color(44, 117, 255);
    public static final Color HOT_PINK = new Color(255, 20, 147);
    public static final Color TOXIC_GREEN = new Color(164, 255, 99);
    public static final Color DEEP_PURPLE = new Color(98, 0, 234);
    public static final Color BURN_ORANGE = new Color(255, 87, 34);
    public static final Color CYBER_YELLOW = new Color(255, 211, 0);
    public static final Color NEON_PURPLE = new Color(177, 58, 255);
    public static final Color NEON_GREEN = new Color(57, 255, 20);
    public static final Color NEON_BLUE = new Color(0, 255, 255);
    public static final Color NEON_RED = new Color(255, 16, 60);
    public static final Color NEON_YELLOW = new Color(255, 255, 0);
    public static final Color NEON_ORANGE = new Color(255, 153, 51);
    public static final Color NEON_PINK = new Color(255, 0, 255);
    public static final Color NEON_PURPLE_ALT = new Color(191, 0, 255);


    // 🌌 فضا و تاریکی
    public static final Color MIDNIGHT_BLUE = new Color(25, 25, 112);
    public static final Color SLATE_GRAY = new Color(112, 128, 144);
    public static final Color DEEP_BLACK = new Color(15, 15, 15);


    // 🌿 طبیعت
    public static final Color FOREST_GREEN = new Color(34, 139, 34);
    public static final Color EARTH_BROWN = new Color(139, 69, 19);
    public static final Color MINT = new Color(152, 255, 152);
    public static final Color SKY_BLUE = new Color(135, 206, 235);
    public static final Color SAND = new Color(255, 229, 180);
    public static final Color MOSS_GREEN = new Color(107, 142, 35);
    public static final Color CLAY = new Color(210, 180, 140);
    public static final Color STONE = new Color(119, 136, 153);
    public static final Color TREE_BROWN = new Color(101, 67, 33);
    public static final Color RUST = new Color(183, 65, 14);
    public static final Color WHEAT = new Color(245, 222, 179);


    // 🔥 اکشن و آتش
    public static final Color FIERY_ORANGE = new Color(255, 69, 0);
    public static final Color CRIMSON_RED = new Color(220, 20, 60);
    public static final Color LAVA_RED = new Color(255, 80, 0);
    public static final Color CHARCOAL = new Color(54, 69, 79);
    public static final Color EMBER = new Color(255, 97, 56);
    public static final Color ASH_GRAY = new Color(178, 190, 181);


    // ❄️ یخی و سرد
    public static final Color ICE_BLUE = new Color(173, 216, 230);
    public static final Color SNOW_WHITE = new Color(250, 250, 250);
    public static final Color POLAR_BLUE = new Color(170, 240, 255);
    public static final Color GLACIER = new Color(200, 255, 255);
    public static final Color FROST = new Color(240, 248, 255);
    public static final Color ICEBERG = new Color(180, 220, 230);


    // 🌈 متفرقه برای تنوع بیشتر
    public static final Color LAVENDER = new Color(230, 230, 250);
    public static final Color GOLD = new Color(255, 215, 0);
    public static final Color TURQUOISE = new Color(64, 224, 208);
    public static final Color CORAL = new Color(255, 127, 80);
    public static final Color PASTEL_PINK = new Color(255, 192, 203);
    public static final Color PASTEL_PURPLE = new Color(221, 160, 221);
    public static final Color PASTEL_GREEN = new Color(152, 251, 152);
    public static final Color PASTEL_YELLOW = new Color(255, 255, 224);
    public static final Color PASTEL_BLUE = new Color(173, 216, 230);


    // 🔄 رندوم‌سازی (اگه خواستی)
    public static Color randomColor() {
        return new Color((int)(Math.random()*255),
                (int)(Math.random()*255),
                (int)(Math.random()*255));
    }

    private GameColors() {
        // static-only utility class
    }
}
