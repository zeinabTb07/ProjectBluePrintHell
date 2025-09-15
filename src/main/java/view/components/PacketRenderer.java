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
        g2d.setColor(getPacketColor(packet));
        g2d.draw(packet.getShape());
    }
    private Color getPacketColor(Packet packet){
        double resolution = 1 - 0.8*(Math.min(1 , Math.divideExact(packet.getNoise(),packet.getSize())));
        Color color ;
        if(packet.isTrojan()){
            color = Constants.Colors.TROJAN_PACKET;
        } else  if(packet instanceof MessagerPacket){
            PacketRecord.ColossusPackets c = ((MessagerPacket)packet).getParentColossusId();
            if(c!=null){
                color = getColorFromUUID(c.uuid());
            } else color = Constants.Colors.PACKET;
        } else color = Constants.Colors.PACKET;
        return new Color(
                (float) color.getRed() / 255f,
                (float) color.getGreen() / 255f,
                (float) color.getBlue() / 255f,
                (float) resolution
        );
    }
    private  Color getColorFromUUID(UUID uuid) {
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
