package controller;

import model.enums.PortType;
import model.interfaces.Updatable;
import model.objects.packets.Connection;
import model.objects.packets.MassagerPacket;
import model.objects.packets.Packet;
import model.objects.systems.NetworkSystem;
import model.objects.systems.OutputPort;
import model.objects.systems.RooterSystem;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PacketController{
    private List<Packet> packets;
    public PacketController(ArrayList<Packet> packets){
        this.packets = packets;
    }


    public void updatePackets(double deltaTime) {
        for (Packet packet : new ArrayList<>(packets)) { // استفاده از کپی برای جلوگیری از ConcurrentModificationException
            if (packet == null) continue;

            // حالت ۱: پکت روی یک سیستم است (اتصال ندارد)
            if (packet.getCurrentConnection() == null) {
                handlePacketOnSystem((MassagerPacket) packet);
            }
            // حالت ۲: پکت روی اتصال در حال حرکت است
            else if (!isPacketReachedEnd(packet)) {
                handlePacketOnConnection(packet, deltaTime);
            }
            // حالت ۳: پکت به مقصد رسیده و باید به سیستم تحویل داده شود
            else {
                handlePacketReachedDestination(packet);
            }
        }
    }

    // حالت ۱: پکت روی سیستم است و باید اتصال بعدی را پیدا کند
    private void handlePacketOnSystem(MassagerPacket packet) {
        Connection connection = getProperConnection(packet);
        if (connection != null) {
            packet.setCurrentConnection(connection);
            packet.setDistancePassedOnConnection(0); // Reset distance
        } else {
            // اگر اتصالی پیدا نشد، پکت را در سیستم نگه دارید (مثلاً برای روترها)
            NetworkSystem currentSystem = packet.getCurrentSystem();
            if (currentSystem instanceof RooterSystem) {
                ((RooterSystem) currentSystem).addPacket(packet);
                packets.remove(packet); // از لیست عمومی پکت‌ها حذف شود
            }
        }
    }

    // حالت ۲: پکت روی اتصال در حال حرکت است
    private void handlePacketOnConnection(Packet packet, double deltaTime) {
        // حرکت پکت بر اساس سرعت و شتاب
        packet.moveNormal(deltaTime);

        // بررسی اختلال (نویز)
        if (isPacketDisruptedByNoise(packet)) {
            resetPacket(packet); // یا هر واکنش دیگر
            packets.remove(packet);
            return;
        }

        // بررسی سقوط پکت (مثلاً اگر از محدوده خارج شود)
        if (isPacketFallen(packet)) {
            packets.remove(packet);
            return;
        }
    }

    // حالت ۳: پکت به مقصد رسیده است
    private void handlePacketReachedDestination(Packet packet) {
        NetworkSystem targetSystem = packet.getCurrentConnection().getTarget().getParentSystem();
        packet.setCurrentSystem(targetSystem);
        packet.setCurrentConnection(null); // اتصال را قطع کنید

        // اگر سیستم مقصد روتر است، پکت را به آن تحویل دهید
        if (targetSystem instanceof RooterSystem) {
            ((RooterSystem) targetSystem).addPacket(packet);
            packets.remove(packet); // از لیست عمومی پکت‌ها حذف شود
        }
    }
    private Connection getProperConnection(Packet p) {
        MassagerPacket packet = (MassagerPacket) p;
        NetworkSystem system = packet.getCurrentSystem();

        return system.getOutputPorts().getOrDefault(packet.getType().getShape(), new ArrayList<>())
                .stream()
                .filter(Objects::nonNull)
                .map(OutputPort::getConnection)
                .filter(conn -> conn != null && !conn.isBusy())
                .findFirst()
                .orElseGet(() ->
                        system.getOutputPorts().values()
                                .stream()
                                .flatMap(List::stream)
                                .map(OutputPort::getConnection)
                                .filter(conn -> conn != null && !conn.isBusy())
                                .findFirst()
                                .orElse(null)
                );
    }

    private void sendPacket(Packet packet){
        Connection connection = getProperConnection(packet);
        if(connection!=null){
            packet.setCurrentConnection(connection);
        }
    }

    private boolean isPacketFallen(Packet packet){
        return packet.getCenterOfMass().distance(new Point(0 , 0))>packet.getSize();
    }

    private boolean isPacketDisruptedByNoise(Packet packet){
        return packet.getNoise() > packet.getSize();
    }

    private boolean isPacketReachedEnd(Packet packet){
        Connection connection = packet.getCurrentConnection();
        return Math.sqrt(packet.getDistancePassedOnConnection() - connection.getLength()) < 1;
    }

    private void resetPacket(Packet packet){
        packet.setNoise(0);
        packet.setDistancePassedOnConnection(0);
        packet.setCenterOfMass(new Point(0 , 0));
    }
}
