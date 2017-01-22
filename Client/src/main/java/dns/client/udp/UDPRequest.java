package dns.client.udp;

import dns.client.gui.AppGUI;
import dns.client.tcp.TCPRequest;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.Charset;

/**
 * Created by mrchebik on 07.01.17.
 */
public class UDPRequest {
    public static void sendUDP(String server, int port, String query) {
        DatagramSocket client = null;
        try {
            client = new DatagramSocket();

            byte[] sendByte = query.getBytes();
            byte[] receiveByte = new byte[32];

            DatagramPacket sender = new DatagramPacket(sendByte, sendByte.length, InetAddress.getByName(server), port);
            client.send(sender);

            DatagramPacket receiver = new DatagramPacket(receiveByte, receiveByte.length);
            client.receive(receiver);

            String receiveLine = new String(receiver.getData(), Charset.forName("UTF-8")).trim();

            AppGUI.outPut(query, receiveLine.equals("TC: true") ? "Response is so big..." : receiveLine, "UDP", receiveLine.equals("TC: true"));

            if (receiveLine.equals("TC: true")) {
                TCPRequest.sendTCP(server, port, query);
            }
        } catch(Exception e) {
            e.getStackTrace();
        }
        if (client != null) {
            client.close();
        }
    }
}
