package client.view.components;

import client.Constants;
import client.view.GameShape;
import shared.model.objects.packets.MessagerPacket;
import shared.model.objects.packets.Packet;
import shared.api.enums.PacketType;
import shared.api.service.mapper.PacketRecord;

import java.awt.*;
import java.util.UUID;

public class PacketRenderer {
    public void render(Graphics2D g2d , Packet packet){
        g2d.setColor(getPacketColor(packet));
        g2d.draw(getShape(packet.getType() , packet.getAbsolutePoint()));
    }
    private Shape getShape(PacketType type , Point point){
        switch (type) {
            case BIG_PACKET1:
                return GameShape.TITAN.getShape(point);
            case BIG_PACKET2:
                return GameShape.RANGAROK.getShape(point);
            case RECTANGLE:
                return GameShape.SQUARE.getShape(point);
            case TRIANGLE:
                return GameShape.TRIANGLE.getShape(point);
            case PROTECTED:
                return GameShape.LOCK.getShape(point);
            case BIT_PACKET:
                return GameShape.BITE.getShape(point);
            case CLASSIFIED1:
                return GameShape.SPIRIT.getShape(point);
            case CLASSIFIED2:
                return GameShape.PHANTOM.getShape(point);
            default:
                throw new IllegalArgumentException("Incorrect packet type");
        }
    }
    private Color getPacketColor(Packet packet){
        Color color ;
        if(packet.isTrojan()){
            color = Constants.Colors.TROJAN_PACKET;
        } else  if(packet instanceof MessagerPacket){
            PacketRecord.ColossusPackets c = ((MessagerPacket)packet).getParentColossusId();
            if(c!=null){
                color = getColorFromUUID(c.uuid());
            } else color = Constants.Colors.PACKET;
        } else color = Constants.Colors.PACKET;
        return color;
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
