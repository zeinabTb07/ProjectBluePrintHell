package events;

public class ShopEvents {
    public enum PowerUpType {
        STOP_IMPACT("O' Atar", 3, 10.0, "Disables Impact waves for 10 seconds"),
        STOP_COLLISION("O' Airyaman", 4, 5.0, "Disables packet collisions in the network for 5 seconds"),
        CLEAR_NOISE("O' Anahita", 5, 0.0, "Reduces noise of all network packets to zero");

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
