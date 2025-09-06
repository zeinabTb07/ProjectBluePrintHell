package view.components;

import model.constants.Constants;
import model.objects.packets.MessagerPacket;
import model.objects.packets.Packet;
import utils.PacketRecord;

import java.awt.*;
import java.util.UUID;

public class PacketRenderer {
    public void render(Graphics2D g2d , Packet packet){
        packet.update();
        if(packet.isTrojan()){
            g2d.setColor(Constants.Colors.TROJAN_PACKET);
        } else  if(packet instanceof MessagerPacket){
            PacketRecord.ColossusPackets c = ((MessagerPacket)packet).getParentColossusId();
            if(c!=null){
                g2d.setColor(getColorFromUUID(c.uuid()));
            }
        } else g2d.setColor(Constants.Colors.PACKET);
        g2d.draw(packet.getShape());
    }
    public  Color getColorFromUUID(UUID uuid) {
        if(uuid==null) return Constants.Colors.PACKET;
        long msb = uuid.getMostSignificantBits();
        long lsb = uuid.getLeastSignificantBits();

        long mixed = msb ^ lsb;

        int r = (int)((mixed >> 16) & 0xFF);
        int g = (int)((mixed >> 32) & 0xFF);
        int b = (int)((mixed >> 48) & 0xFF);

        return new Color(r, g, b);
    }
}
