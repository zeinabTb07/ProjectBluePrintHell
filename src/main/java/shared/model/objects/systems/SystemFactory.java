package shared.model.objects.systems;

import client.Constants;
import shared.api.enums.SystemType;

import java.awt.*;

public class SystemFactory {
    public static NetworkSystem createSystem(SystemType type, int x, int y) {
        NetworkSystem sys = null;
        Point point = new Point((int)(x * Constants.SCALE), (int)(y * Constants.SCALE));
        switch (type){
            case SystemType.NORMAL:
                sys = new LinkSystem(point);
                break;
            case SystemType.VPN:
                sys = new VPNSystem(point);
                break;
            case SystemType.DDOS:
                sys = new ChaosSystem(point);
                break;
            case SystemType.SPY:
                sys = new SpySystem(point);
                break;
            case SystemType.ANTI_TROJAN:
                sys = new AntiTrojanSystem(point);
                break;
            case SystemType.DISTRIBUTE:
                sys = new DistributeSystem(point);
                break;
            case SystemType.MERGE:
                sys = new MergeSystem(point);
                break;
            case SystemType.ROOTER:
                sys = new RooterSystem(point);
                break;
            default:
                throw new RuntimeException("Invalid SystemType");
        }
        return sys;
    }
}
