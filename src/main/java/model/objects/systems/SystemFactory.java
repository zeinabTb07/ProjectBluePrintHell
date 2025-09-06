package model.objects.systems;

import model.constants.Constants;
import model.enums.SystemType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

public class SystemFactory {
    private static final Logger logger = LoggerFactory.getLogger(SystemFactory.class);
    public static NetworkSystem createSystem(SystemType type, int x, int y) {
        NetworkSystem sys = null;
        Point point = new Point((int)(x * Constants.SCALE), (int)(y * Constants.SCALE));
        switch (type){
            case SystemType.LINK:
                sys = new LinkSystem(point);
            case SystemType.VPN:
                sys = new VPNSystem(point);
            case SystemType.CHAOS:
                sys = new ChaosSystem(point);
            case SystemType.SPY:
                sys = new SpySystem(point);
            case SystemType.ANTI_TROJAN:
                sys = new AntiTrojanSystem(point);
            case SystemType.DISTRIBUTE:
                sys = new DistributeSystem(point);
            case SystemType.MERGE:
                sys = new MergeSystem(point);
            case SystemType.ROOTER:
                sys = new RooterSystem(point);
            default:
                logger.error("Invalid packet type");
        }
        return sys;
    }

}
