package events;

public class ShopEvents {
    public enum PowerUpType {
        ZERO_ACCELERATION("Scroll Of Aergia", 10, 20.0, "Sets packet acceleration to zero at a selected point for 20 seconds"),
        RELOCATE_SYSTEM("Scroll of Sisyphus ", 15, 0.0, "Relocates a non-rooter system within network constraints"),
        ALIGN_CENTER("Scroll Of Eliphas", 20, 30.0, "Realigns packet centers to wire trajectory for 30 seconds"),
        HELPER_POINT("Scroll Of Path" , 1 , 0 , "Add a helper Point to make a curve on your connection");
        private final String name;
        private final int price;
        private final double duration;
        private final String description;

        PowerUpType(String name, int price, double duration, String description) {
            this.name = name;
            this.price = price;
            this.duration = duration;
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public int getPrice() {
            return price;
        }

        public double getDuration() {
            return duration;
        }

        public String getDescription() {
            return description + " (Cost: " + price + " coins)";
        }
    }

    public record PowerUpEvent(PowerUpType powerUpType) {
    }
}
