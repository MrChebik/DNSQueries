package dns.client.tcp;

import dns.client.gui.AppGUI;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by mrchebik on 07.01.17.
 */
public class TCPRequest {
    public static void sendTCP(String server, int port, String query) throws IOException {
        Socket socket = null;
        try {
            socket = new Socket(server, port);

            socket.getOutputStream().write(query.getBytes());

            byte buffer[] = new byte[64*1024];
            int read = socket.getInputStream().read(buffer);
            String data = new String(buffer, 0, read);

            AppGUI.outPut(query, data, "TCP", false);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }
    }
}
