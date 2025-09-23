package shared.api.dto;

import java.util.UUID;

public class GameInfoDTO {
    private UUID id;
    private long spentTime;
    private int coin;

    public GameInfoDTO(long spentTime) {
        this.spentTime = spentTime;
    }

    public UUID getId(){ return id; }
    public long getSpentTime() {
        return spentTime;
    }

    public void setSpentTime(long spentTime) {
        this.spentTime = spentTime;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }
}
