package events;

public class ShopEvents {
    public record StopImpactEvent() {
        public static final int PRICE = 3;
        public int price() {
            return PRICE;
        }
    }

    public record StopCollisionEvent() {
        public static final int PRICE = 4;
        public int price() {
            return PRICE;
        }
    }

    public record ClearNoiseEvent() {
        public static final int PRICE = 5;
        public int price() {
            return PRICE;
        }
    }
}
